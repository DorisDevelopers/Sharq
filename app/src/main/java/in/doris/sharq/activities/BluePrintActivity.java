package in.doris.sharq.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.sql.Date;

import in.doris.sharq.db.beans.BlueprintBean;
import in.doris.sharq.db.datasources.BlueprintDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;
import in.doris.sharq.util.DatePickerFragment;

public class BluePrintActivity extends AppCompatActivity {
    AppCompatEditText blueprint, adat;
    Button next;
    SharqDbHelper dbHelper;
    Spinner sts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blue_print);
        Intent intent = getIntent();
        getAllWidgets();
        dbHelper = SharqDbHelper.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        blueprint = (AppCompatEditText) findViewById(R.id.blueprint);
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


    private void getAllWidgets() {
//        id = (EditText) findViewById(R.id._id);
         blueprint = (AppCompatEditText) findViewById(R.id.blueprint);
       sts = (Spinner) findViewById(R.id.sts);
         adat = (AppCompatEditText) findViewById(R.id.adat);

         next = (Button) findViewById(R.id.ok);
    }

    public void onButtonClick(View view) {
        String esst;
        Date edat = null;
        if ( blueprint.getText().toString().equals("") ) {
            Toast.makeText(getApplicationContext(), "Enter Blueprint", Toast.LENGTH_LONG).show();
        }else {
            String ebp = blueprint.getText().toString();

            esst = sts.getSelectedItem().toString();
            if(esst.equalsIgnoreCase("Achieved") && adat.getText().toString().equals("")){
                Toast.makeText(getApplicationContext(), "Enter Blueprint", Toast.LENGTH_LONG).show();
            }else{
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

        }
        finish();

    }
}


