package in.doris.sharq.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

import in.doris.sharq.activities.R;
import in.doris.sharq.db.beans.NamelistBean;
import in.doris.sharq.db.datasources.NamelistDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

public class NameListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_name_list);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        SharqDbHelper db = SharqDbHelper.getInstance(getApplicationContext());
        android.widget.LinearLayout parentLayout;
//        LinearLayout layoutDisplayPeople;
        displayallNames();
    }

    private void displayallNames(){
        //LinearLayout inflateParentView;
        //SharqDbHelper db = SharqDbHelper.getInstance(getApplicationContext());
//        android.widget.LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
////        parentLayout.removeAllViews();
//        SharqDbHelper db = SharqDbHelper.getInstance(getApplicationContext());
//        NamelistDataSource nldsc = new NamelistDataSource(getApplicationContext());
//        List<NamelistBean> contacts = nldsc.getAllNamelists();
//        if (contacts.size() > 0){
        NamelistDataSource namelistDS = new NamelistDataSource(getApplicationContext());
        ArrayList<NamelistBean> namelists = new ArrayList<>();
        final View view = LayoutInflater.from(this).inflate(R.layout.full_name_list, null);
        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout);
        TableRow rowHeader = new TableRow(getApplicationContext());
        new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        String[] headerText={"S.No.","Name", "Location", "Cat", "Contact"};
        for(String c:headerText) {
            TextView tv = new TextView(this);
            tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT));
            tv.setGravity(Gravity.CENTER);
            tv.setTextSize(18);
            tv.setPadding(5, 5, 5, 5);
            tv.setText(c);
            rowHeader.addView(tv);
        }
        tableLayout.addView(rowHeader);

        // Get data from sqlite database and add them to the table
        // Open the database for reading
        // SQLiteDatabase dbhelper = db.getReadableDatabase();
        // Start the transaction.
        //dbhelper.beginTransaction();


        try
        {
            namelistDS.open();
            namelists = (ArrayList<NamelistBean>) namelistDS.getAllNamelists();
            if(namelists.size() >0)
            {
                for (NamelistBean namelstObj:namelists) {
                    // Read columns data
                    final Long id= namelstObj.getId();
                    final String name= namelstObj.getName();
                    final String loc= namelstObj.getPlace();
                    String cat= namelstObj.getCat();
                    String phone= String.valueOf(namelstObj.getPhone());

                    // dara rows
                    TableRow row = new TableRow(getApplicationContext());
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Do Stuff
                            final Dialog dialog = new Dialog(NameListActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog);
                            dialog.setTitle(R.string.star);
                            Button yes = (Button) dialog.findViewById(R.id.yes);
                            Button no = (Button) dialog.findViewById(R.id.no);
                            final RatingBar star = (RatingBar) dialog.findViewById(R.id.ratingBar);
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    NamelistBean nb = new NamelistBean();
                                    nb.setStar(String.valueOf(star.getRating()));
                                    nb.setId(id);
                                    NamelistDataSource nds = new NamelistDataSource(getApplicationContext());
                                    nds.updateNamelistAlternate(nb);
                                    Toast.makeText(getApplicationContext(), "Star is given.", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();

                                }
                            });
                            no.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(getApplicationContext(), "Star is not given.", Toast.LENGTH_SHORT).show();

                                    dialog.dismiss();
                                }
                            });

                            // Create the AlertDialog object and return it
                            final Window window = dialog.getWindow();
                            window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                            window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            dialog.show();
                        }
                    });
                    row.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(NameListActivity.this);
                            builder.setMessage(R.string.follow)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // FIRE ZE MISSILES!
                                            Intent intent = new Intent(NameListActivity.this, FollowupActivity.class);
//                                                intent.putExtra(DbContract.Namelist.COLUMN_NAME, ename);
//                                                intent.putExtra(DbContract.Namelist.COLUMN_PHONE, ephone);
//                                                intent.putExtra(DbContract.Namelist.COLUMN_PLACE, eplace);
//                                                intent.putExtra(DbContract.Namelist.COLUMN_CAT, ecat);
//                                                intent.putExtra(DbContract.Namelist.COLUMN_STS, ests);
//                                                setResult(RESULT_OK , intent);
//                                                finish();
                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog

                                        }
                                    });
                            // Create the AlertDialog object and return it
                            builder.create();
                            return true;
                        }
                    });
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={id+"",name+" ",loc, cat, phone};
                    for(String text:colText) {
                        TextView tv = new TextView(this);
                        tv.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,
                                TableRow.LayoutParams.WRAP_CONTENT));
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextSize(16);
                        tv.setPadding(5, 5, 5, 5);
                        tv.setText(text);
                        row.addView(tv);
                    }
                    tableLayout.addView(row);

                }

            }
            //dbhelper.setTransactionSuccessful();

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            namelistDS.close();
            //dbhelper.endTransaction();
            // End the transaction.
            //dbhelper.close();
            // Close database
        }
//            parentLayout.addView(view);
//
//    }else{
//            TextView  tvNoRecordsFound = (TextView) findViewById(R.id.tvNoRecordsFound);
//            tvNoRecordsFound.setVisibility(View.VISIBLE);
//        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_name_list, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
//        add new record
        if (id == R.id.add) {
//            getLayoutInflater().inflate(R.layout.activity_name_list, null);
//            Button ok = (Button) findViewById(R.id.ok);

            Intent intent = new Intent(this, ManipulateNamelistActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.name_list) {
            Intent intent = new Intent(this, NameListActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.follow_up_list) {
            Intent intent = new Intent(this, FollowupActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}