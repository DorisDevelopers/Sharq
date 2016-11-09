package in.doris.sharq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;

import static in.doris.sharq.activities.LoginActivity.KEY_EMAIL;
import static in.doris.sharq.activities.LoginActivity.KEY_NAME;

public class RegisterActivity extends AppCompatActivity {
    AppCompatEditText fldName, fldEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_register);
        //Get name and email from login activity.
        String name = "";
        String email = "";
        Intent intent = getIntent();
        if (null != intent) {
            name = intent.getStringExtra(KEY_NAME);
            email = intent.getStringExtra(KEY_EMAIL);
            fldName = (AppCompatEditText) findViewById(R.id.name);
            fldName.setText(name);

            fldEmail = (AppCompatEditText) findViewById(R.id.email);
            fldEmail.setText(email);

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
        Intent intent = new Intent(this, DisplayMenuActivity.class);
        startActivity(intent);
    }
}

