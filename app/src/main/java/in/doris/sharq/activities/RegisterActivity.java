package in.doris.sharq.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;

import in.doris.sharq.constants.SharqConstants;
import in.doris.sharq.db.beans.MstUserBean;
import in.doris.sharq.db.datasources.MstUserDataSource;
import in.doris.sharq.util.DatePickerFragment;

import static in.doris.sharq.constants.SharqConstants.KEY_EMAIL;
import static in.doris.sharq.constants.SharqConstants.KEY_LNAME;
import static in.doris.sharq.constants.SharqConstants.KEY_NAME;

public class RegisterActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    AppCompatEditText fldName, fldLname, fldIr, fldEmail, fldPhone, fldJoining, fldLeader;
    Spinner fldCcy, fldRank;
    String name, lName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        //Get name and email from login activity.
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (null != intent) {
            name = intent.getStringExtra(KEY_NAME);
            lName = intent.getStringExtra(KEY_LNAME);
            email = intent.getStringExtra(KEY_EMAIL);
            fldName = (AppCompatEditText) findViewById(R.id.name);
            fldName.setText(name + " " + lName);
            //fldName.setFocusable(false);
            fldName.setEnabled(false);

            fldEmail = (AppCompatEditText) findViewById(R.id.email);
            fldEmail.setText(email);
            //fldEmail.setFocusable(false);
            fldEmail.setEnabled(false);

            fldPhone = (AppCompatEditText) findViewById(R.id.phone);

            fldJoining = (AppCompatEditText) findViewById(R.id.joining_date);
            fldJoining.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // TODO Auto-generated method stub
                    DatePickerFragment picker = new DatePickerFragment();
                    picker.setDateField(fldJoining);
                    picker.show(getSupportFragmentManager(), "datePicker");
                }
            });

            fldLeader = (AppCompatEditText) findViewById(R.id.leader_name);

            fldIr =  (AppCompatEditText) findViewById(R.id.ir);

            fldCcy = (Spinner) findViewById(R.id.ccy);
            fldCcy.setOnItemSelectedListener(this);

            fldRank = (Spinner) findViewById(R.id.rank);
            fldCcy.setOnItemSelectedListener(this);
        }
        /*Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);*/

    }
    public void startGoal(View v){
        Intent intent = new Intent(RegisterActivity.this, AddGoalsActivity.class);
        startActivity(intent);
    }
    public void onBluePrint(View v){
        Intent intent = new Intent(RegisterActivity.this, BluePrintActivity.class);
        startActivity(intent);
    }

    public void startDisplayMenu(View v){
        Intent intent = new Intent(this, DisplayMenuActivity.class);
        startActivity(intent);
    }


    public void onSubmit(View v){
        if(null == fldName || null == fldPhone || null == fldEmail || null == fldJoining || null == fldLeader
                || "".equals(fldName.getText().toString()) || "".equals(fldPhone.getText().toString())
                || "".equals(fldEmail.getText().toString())|| "".equals(fldJoining.getText().toString())
                || "".equals(fldLeader.getText().toString())){
            new AlertDialog.Builder(RegisterActivity.this)
                    .setTitle("Details Missing")
                    .setMessage("All the fields are Mandatory.")
                    .setCancelable(false)
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Whatever...
                        }
                    }).create().show();
        }else{
            if(!createUser()){
                //Action to perform when user creation fails, preferably return to same page and try again.
            }else {
                startGoal(v);
                //startDisplayMenu(v);
            }
        }

    }
    private boolean createUser(){

        MstUserDataSource userDs = new MstUserDataSource(getApplicationContext());
        MstUserBean userBean = new MstUserBean();
        try{
            Toast.makeText(getApplicationContext(), fldJoining.getText().toString(), Toast.LENGTH_LONG).show();
            userBean.setName(name);
            userBean.setLname(lName);
            userBean.setPhone(Long.parseLong(fldPhone.getText().toString()));
            userBean.setEmail(email);
            SharqConstants.DATE_FORMAT.parse(fldJoining.getEditableText().toString());
            Date jDate = new Date(SharqConstants.DATE_FORMAT.parse(fldJoining.getText().toString()).getTime());
            userBean.setDatjnd(jDate);
            userBean.setCcy(String.valueOf(fldCcy.getSelectedItem()));
            userBean.setIr(fldIr.getText().toString());
            userBean.setRank(String.valueOf(fldRank.getSelectedItem()));
            userDs.open();
            userDs.createUser(userBean);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

