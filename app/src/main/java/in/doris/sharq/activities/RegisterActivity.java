package in.doris.sharq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.content_register);
        Intent intent = getIntent();
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

