package in.doris.sharq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import in.doris.sharq.activities.R;
import in.doris.sharq.db.beans.BlueprintBean;
import in.doris.sharq.db.beans.GoalBean;
import in.doris.sharq.db.datasources.BlueprintDataSource;
import in.doris.sharq.db.datasources.GoalDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import java.sql.Date;

public class BluePrintActivity extends AppCompatActivity {
    EditText blueprint, sts, adat;
    Button next;
    SharqDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_print);
        Intent intent = getIntent();
        getAllWidgets();
            dbHelper = SharqDbHelper.getInstance(getApplicationContext());
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            blueprint = (EditText) findViewById(R.id.blueprint);
            sts = (EditText) findViewById(R.id.sts);
            adat = (EditText) findViewById(R.id.adat);

        }


    private void getAllWidgets() {
//        id = (EditText) findViewById(R.id._id);
         blueprint = (EditText) findViewById(R.id.blueprint);
       sts = (EditText) findViewById(R.id.sts);
         adat = (EditText) findViewById(R.id.adat);

         next = (Button) findViewById(R.id.ok);
    }

    public void onButtonClick(View view) {
        String esst = null;
        Date edat = null;
        if ( blueprint.getText().toString().equals("") ) {
            Toast.makeText(getApplicationContext(), "Enter Blueprint", Toast.LENGTH_LONG).show();}
         else {
            String ebp = blueprint.getText().toString();
            if(!sts.getText().toString().equals("")){
                esst = sts.getText().toString();
            }
            if(!adat.getText().toString().equals("")) {
                edat = java.sql.Date.valueOf(adat.getText().toString());
            }
            BlueprintBean bb = new BlueprintBean();
            bb.setBlueprint(ebp);
            bb.setSts(esst);
            bb.setAdat(edat);
            BlueprintDataSource gds = new BlueprintDataSource(getApplicationContext());
            gds.createBlueprint(bb);
            Toast.makeText(this, "Success. ", Toast.LENGTH_LONG).show();
        }
        finish();

    }
}


