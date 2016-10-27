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
import android.widget.RatingBar;
import android.widget.Toast;
import in.doris.sharq.activities.R;

import in.doris.sharq.db.beans.NamelistBean;
import in.doris.sharq.db.contracts.DbContract;
import in.doris.sharq.db.datasources.NamelistDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

public class ManipulateNamelistActivity extends AppCompatActivity {
    EditText name, phone, place, cat,  sts;
    RatingBar ratingBar;
    Button next;
    SharqDbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manipulate_namelist);
        Intent intent = getIntent();
//        bindWidgetsWithEvent();
        dbHelper = SharqDbHelper.getInstance(getApplicationContext());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        next = (Button) findViewById(R.id.ok);
//        id = (EditText) findViewById(R.id._id);
        name = (EditText) findViewById(R.id.name);
        phone = (EditText) findViewById(R.id.phone);
        place = (EditText) findViewById(R.id.place);
//        remark = (EditText) findViewById(R.id.remark);
        cat = (EditText) findViewById(R.id.cat);
        sts = (EditText) findViewById(R.id.sts);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);

    }
    public void onRatingBar(View v){
        String rating = String.valueOf(ratingBar.getRating());
    }


    public void onButtonClick(View view) {
        String  ename = null,
                eplace = null,
                ecat = null,
                //eremark,
                ests = null;
        Long    ephone = null;
        NamelistDataSource gds = new NamelistDataSource(getApplicationContext());
        NamelistBean gb = new NamelistBean();
        try{
            if ( null==name || null == place
                    || null == cat || "".equals(name.getText().toString()) || "".equals(place.getText().toString())
                    || "".equals(cat.getText().toString()))
            {
                Toast.makeText(getApplicationContext(), "Name/Place/Catagory can't be empty.", Toast.LENGTH_LONG).show();
            } else {
                ename = name.getText().toString();
                eplace= place.getText().toString();
                if(null!=phone && !"".equalsIgnoreCase(phone.getText().toString())) {
                    ephone = Long.parseLong(phone.getText().toString());
                    gb.setPhone(ephone);
                }
                ecat= cat.getText().toString();
                //if(null!=remark && !"".equalsIgnoreCase(remark.getText().toString())) {
                    //eremark = remark.getText().toString();
                    //gb.setRemark(eremark);

                //}
                if(null!=sts && !"".equalsIgnoreCase(sts.getText().toString())) {
                    ests = sts.getText().toString();
                    gb.setSts(ests);
                }
                gb.setName(ename);
                gb.setPlace(eplace);
                gb.setCat(ecat);
                //gb.setStar(String.valueOf(ratingBar.getRating()));
                gds.open();
                gds.createNamelist(gb);
                Toast.makeText(this, gb.getName()+" added to Namelist ", Toast.LENGTH_LONG).show();

            }
            Intent intent= new Intent(ManipulateNamelistActivity.this, NameListActivity.class);
            intent.putExtra(DbContract.Namelist.COLUMN_NAME, ename);
            intent.putExtra(DbContract.Namelist.COLUMN_PHONE, ephone);
            intent.putExtra(DbContract.Namelist.COLUMN_PLACE, eplace);
            intent.putExtra(DbContract.Namelist.COLUMN_CAT, ecat);
            intent.putExtra(DbContract.Namelist.COLUMN_STS, ests);
            setResult(RESULT_OK , intent);
//            Toast.makeText(this, "Record is added ", Toast.LENGTH_LONG).show();

           finish();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            gds.close();
        }

    }

}
