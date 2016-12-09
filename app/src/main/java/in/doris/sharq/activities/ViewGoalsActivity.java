package in.doris.sharq.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
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

import in.doris.sharq.db.beans.GoalBean;
import in.doris.sharq.db.datasources.GoalDataSource;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

public class ViewGoalsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private GoogleApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_goals);
        Intent intent = getIntent();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        SharqDbHelper db = SharqDbHelper.getInstance(getApplicationContext());
        android.widget.LinearLayout parentLayout;
        displayallGoals();
    }

    private void displayallGoals(){
        GoalDataSource goalDS = new GoalDataSource(getApplicationContext());
        ArrayList<GoalBean> goals = new ArrayList<>();
        final View view = LayoutInflater.from(this).inflate(R.layout.full_goal_list, null);
        TableLayout tableLayout=(TableLayout)findViewById(R.id.tablelayout);
        TableRow rowHeader = new TableRow(getApplicationContext());
        new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.WRAP_CONTENT);
        String[] headerText={"S.No.","Goal", "Amount", "Deadline", "Status", "Hit on"};
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
        try
        {
            goalDS.open();
            goals = (ArrayList<GoalBean>) goalDS.getAllGoals();

            if(goals.size() >0)
            {
                for (GoalBean goalObj:goals) {
                    // Read columns data
                    final Long id= goalObj.getId();
                    final String goal= goalObj.getGoal();
                    final String amt= goalObj.getAmt().toString();
                    String ddl= goalObj.getDdl().toString();
                    String sts= goalObj.getSts();
                    String adat= goalObj.getAdat().toString();

                    // dara rows
                    TableRow row = new TableRow(getApplicationContext());
                    row.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //Do Stuff
                            final Dialog dialog = new Dialog(ViewGoalsActivity.this);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            dialog.setContentView(R.layout.dialog);
                            dialog.setTitle(R.string.star);
                            Button yes = (Button) dialog.findViewById(R.id.yes);
                            Button no = (Button) dialog.findViewById(R.id.no);
                            final RatingBar star = (RatingBar) dialog.findViewById(R.id.ratingBar);
                            yes.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                   /* NamelistBean nb = new NamelistBean();
                                    nb.setStar(String.valueOf(star.getRating()));
                                    nb.setId(id);
                                    NamelistDataSource nds = new NamelistDataSource(getApplicationContext());
                                    nds.updateNamelistAlternate(nb);*/
                                    //TODO: action on touching goal
                                    Toast.makeText(getApplicationContext(), "provide action to edit goal", Toast.LENGTH_SHORT).show();
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

                            //TODO: add code for long press
                           /* AlertDialog.Builder builder = new AlertDialog.Builder(ViewGoalsActivity.this);
                            builder.setMessage(R.string.follow)
                                    .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            Intent intent = new Intent(NameListActivity.this, FollowupActivity.class);
//
                                        }
                                    })
                                    .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            // User cancelled the dialog

                                        }
                                    });
                            // Create the AlertDialog object and return it
                            builder.create();*/
                            return true;
                        }
                    });
                    row.setLayoutParams(new TableLayout.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT,
                            TableLayout.LayoutParams.WRAP_CONTENT));
                    String[] colText={id.toString(),goal,amt, ddl, sts, adat};
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

        }
        catch (SQLiteException e)
        {
            e.printStackTrace();

        }
        finally
        {
            goalDS.close();
            //dbhelper.endTransaction();
            // End the transaction.
            //dbhelper.close();
            // Close database
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
