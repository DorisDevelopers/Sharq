package in.doris.sharq.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.sql.Date;
import java.util.Locale;

import in.doris.sharq.activities.R;
import in.doris.sharq.db.beans.GoalBean;
import in.doris.sharq.db.datasources.GoalDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import in.doris.sharq.util.DatePickerFragment;

public class GoalsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    EditText id, goal, amt, ddl, adat;
    Spinner spinner;
    Button next;
    Button finish;
    SharqDbHelper dbHelper;
    private static final String TAG = "goals";
    final Calendar myCalendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals2);
        Intent intent = getIntent();
        getAllWidgets();
//        bindWidgetsWithEvent();
        dbHelper = SharqDbHelper.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
//
//            @Override
//            public void onDateSet(DatePicker view, int year, int monthOfYear,
//                                  int dayOfMonth) {
//                // TODO Auto-generated method stub
//                myCalendar.set(Calendar.YEAR, year);
//                myCalendar.set(Calendar.MONTH, monthOfYear);
//                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                updateLabel();
//            }
//
//        };
        next = (Button) findViewById(R.id.ok);
//        id = (EditText) findViewById(R.id._id);
        goal = (EditText) findViewById(R.id.goal);
        amt = (EditText) findViewById(R.id.amt);
        ddl = (EditText) findViewById(R.id.ddl);
        ddl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerFragment picker = new DatePickerFragment();
                picker.show(getSupportFragmentManager(), "datePicker");
                ddl.setText(picker.updateLabel());
            }
        });
        spinner = (Spinner) findViewById(R.id.sts);
        adat = (EditText) findViewById(R.id.adat);
        adat.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                DatePickerFragment picker = new DatePickerFragment();
                picker.show(getSupportFragmentManager(), "datePicker");
//                new DatePickerDialog(GoalsActivity.this, date, myCalendar
//                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
//                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });



    }


//    private void updateLabel() {
//
//        String myFormat = "MM/dd/yy"; //In which you need put here
//        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
//
//        ddl.setText(sdf.format(myCalendar.getTime()));
//        adat.setText(sdf.format(myCalendar.getTime()));
//    }

//        next.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                setContentView(R.layout.activity_goals2);
//                GoalBean goal= new GoalBean();
//


    private void getAllWidgets() {
//        id = (EditText) findViewById(R.id._id);
        goal = (EditText) findViewById(R.id.goal);
        amt = (EditText) findViewById(R.id.amt);
        spinner = (Spinner) findViewById(R.id.sts);
        spinner.setOnItemSelectedListener(this);
        ddl = (EditText) findViewById(R.id.ddl);
        ddl.setFocusable(false);
        ddl.setClickable(true);

        adat = (EditText) findViewById(R.id.adat);

        next = (Button) findViewById(R.id.ok);

    }

    public void onButtonClick(View view) {
        if ( goal.getText().toString().equals("") || amt.getText().toString().equals("")
                || ddl.getText().toString().equals("") || spinner.getSelectedItem().toString().equals("") || adat.getText().toString().equals(""))
       {
            Toast.makeText(getApplicationContext(), "Add all Fields", Toast.LENGTH_LONG).show();
        } else {
            String egoal = goal.getText().toString();
            Long eamt= Long.parseLong(amt.getText().toString().trim());
//       System.out.print("Date is " +ddl.getText().toString());
//       Log.d(TAG, ddl.getText().toString());
            Date eddl = Date.valueOf(ddl.getText().toString());
            Date eadat = Date.valueOf(adat.getText().toString());
            String esst =  String.valueOf(spinner.getSelectedItem());
            GoalBean gb = new GoalBean();
            gb.setGoal(egoal);
            gb.setAmt(eamt);
            gb.setDdl(eddl);
            gb.setSts(esst);
            gb.setAdat(eadat);
            GoalDataSource gds = new GoalDataSource(getApplicationContext());
            gds.createGoal(gb);
            Toast.makeText(this, "Goal is set. ", Toast.LENGTH_LONG).show();

      }
        finish();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(parent.getContext(),
                parent.getItemAtPosition(position).toString(),
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}