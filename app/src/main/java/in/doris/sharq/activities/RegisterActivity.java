package in.doris.sharq.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.sql.Date;

import in.doris.sharq.db.beans.MstUserBean;
import in.doris.sharq.db.datasources.MstUserDataSource;

import static in.doris.sharq.constants.SharqConstants.KEY_EMAIL;
import static in.doris.sharq.constants.SharqConstants.KEY_LNAME;
import static in.doris.sharq.constants.SharqConstants.KEY_NAME;

public class RegisterActivity extends AppCompatActivity {
    AppCompatEditText fldName, fldLname, fldIr, fldCcy, fldRank, fldEmail, fldPhone, fldJoining, fldLeader;
    String name, lName, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        //Get name and email from login activity.
        /*String name = "";
        String lName = "";
        String email = "";*/
        Intent intent = getIntent();
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

            fldLeader = (AppCompatEditText) findViewById(R.id.leader_name);
        }
        Toolbar myChildToolbar =
                (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myChildToolbar);

    }
    public void onGoal(View v){
        Intent intent = new Intent(RegisterActivity.this, GoalsActivity.class);
        startActivity(intent);
    }
    public void onBluePrint(View v){
        Intent intent = new Intent(RegisterActivity.this, BluePrintActivity.class);
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
                Intent intent = new Intent(this, DisplayMenuActivity.class);
                startActivity(intent);
            }
        }

    }
    private boolean createUser(){

        MstUserDataSource userDs = new MstUserDataSource(getApplicationContext());
        MstUserBean userBean = new MstUserBean();
        try{
            userBean.setName(name);
            userBean.setLname(lName);
            userBean.setPhone(Long.parseLong(fldPhone.getText().toString()));
            userBean.setEmail(email);
            userBean.setDatjnd(Date.valueOf(fldName.getText().toString()));
            userBean.setCcy(fldCcy.getText().toString());
            userBean.setIr(fldIr.getText().toString());
            userBean.setRank(fldRank.getText().toString());
            userDs.open();
            userDs.createUser(userBean);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}

