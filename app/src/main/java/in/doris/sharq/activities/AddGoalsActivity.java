package in.doris.sharq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;
import java.sql.Date;

import in.doris.sharq.db.beans.GoalBean;
import in.doris.sharq.db.datasources.GoalDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import in.doris.sharq.util.DatePickerFragment;

public class AddGoalsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AppCompatEditText id, goal, amt, ddl, adat;
    Spinner sts;
    Button next;
    Button finish;
    SharqDbHelper dbHelper;
    private static final String TAG = "goals";
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);
        Intent intent = getIntent();
        getAllWidgets();
        dbHelper = SharqDbHelper.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        next = (Button) findViewById(R.id.ok);
        goal = (AppCompatEditText) findViewById(R.id.goal);
        amt = (AppCompatEditText) findViewById(R.id.amt);
        ddl = (AppCompatEditText) findViewById(R.id.ddl);
        ddl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerFragment picker = new DatePickerFragment();
                picker.setDateField(ddl);
                picker.show(getSupportFragmentManager(), "datePicker");
            }
        });
        sts = (Spinner) findViewById(R.id.sts);
        adat = (AppCompatEditText) findViewById(R.id.adat);
        adat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerFragment picker = new DatePickerFragment();
                picker.setDateField(adat);
                picker.show(getSupportFragmentManager(), "datePicker");
            }
        });



    }


/*
private void updateLabel() {

String myFormat = "MM/dd/yy"; //In which you need put here
SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

ddl.setText(sdf.format(myCalendar.getTime()));
adat.setText(sdf.format(myCalendar.getTime()));
}
next.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View v) {
setContentView(R.layout.activity_goals);
GoalBean goal= new GoalBean();

*/


    private void getAllWidgets() {
//        id = (EditText) findViewById(R.id._id);
        goal = (AppCompatEditText) findViewById(R.id.goal);
        amt = (AppCompatEditText) findViewById(R.id.amt);
        sts = (Spinner) findViewById(R.id.sts);
        sts.setOnItemSelectedListener(this);
        ddl = (AppCompatEditText) findViewById(R.id.ddl);
        ddl.setFocusable(false);
        ddl.setClickable(true);

        adat = (AppCompatEditText) findViewById(R.id.adat);
        adat.setFocusable(false);
        adat.setClickable(true);

        next = (Button) findViewById(R.id.ok);

    }

    public void onButtonClick(View v) {
        if ( goal.getText().toString().equals("") || amt.getText().toString().equals("")
                || ddl.getText().toString().equals("") || sts.getSelectedItem().toString().equals("") || adat.getText().toString().equals(""))
       {
            Toast.makeText(getApplicationContext(), "Add all Fields", Toast.LENGTH_LONG).show();
        } else {
            String egoal = goal.getText().toString();
            Long eamt= Long.parseLong(amt.getText().toString().trim());
//       System.out.print("Date is " +ddl.getText().toString());
//       Log.d(TAG, ddl.getText().toString());
            Date eddl = Date.valueOf(ddl.getText().toString());
            Date eadat = Date.valueOf(adat.getText().toString());
            String esst =  String.valueOf(sts.getSelectedItem());
            GoalBean gb = new GoalBean();
            gb.setGoal(egoal);
            gb.setAmt(eamt);
            gb.setDdl(eddl);
            gb.setSts(esst);
            gb.setAdat(eadat);
            GoalDataSource gds = new GoalDataSource(getApplicationContext());
            gds.createGoal(gb);
            Toast.makeText(this, "Goal is set. ", Toast.LENGTH_LONG).show();
            viewAddGoals(v);

      }
        //finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void viewAddGoals(View v){
        Intent intent = new Intent(this, ViewGoalsActivity.class);
        startActivity(intent);
    }

}