package in.doris.sharq.db.datasources;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import in.doris.sharq.db.beans.GoalBean;
import in.doris.sharq.db.contracts.DbContract.Goal;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

public class GoalDataSource {
	// Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = { 
    		Goal.COLUMN_ID,
    		Goal.COLUMN_GOAL,
    		Goal.COLUMN_AMT,
    		Goal.COLUMN_DDL,
    		Goal.COLUMN_STS,
    		Goal.COLUMN_ADAT
    };

    public GoalDataSource(Context context)
    {

        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public GoalBean createGoal(GoalBean goalBeanObj) {
        ContentValues values = new ContentValues();
        open();
        values.put(Goal.COLUMN_GOAL, goalBeanObj.getGoal());
        values.put(Goal.COLUMN_AMT, goalBeanObj.getAmt());
        values.put(Goal.COLUMN_DDL, goalBeanObj.getDdl().toString());

        values.put(Goal.COLUMN_STS, goalBeanObj.getSts());
        values.put(Goal.COLUMN_ADAT, goalBeanObj.getAdat().toString());
        long insertId = 0L;
        insertId=database.insert(Goal.TABLE_NAME, null,
                        values);
        Cursor cursor = database.query(Goal.TABLE_NAME,
                        allColumns, Goal.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
        cursor.moveToFirst();
        GoalBean newGoal = cursorToGoal(cursor);
        cursor.close();
        return newGoal;
    }

    public void deleteGoal(GoalBean goalBeanObj) {
        long id = goalBeanObj.getId();
        System.out.println("Goal deletion initiated with id: " + id);
        database.delete(Goal.TABLE_NAME, Goal.COLUMN_ID
                + " = " + id, null);
        System.out.println("Goal deleted with id: " + id);
    }

    public List<GoalBean> getAllGoals() {
        List<GoalBean> goals = new ArrayList<GoalBean>();

        Cursor cursor = database.query(Goal.TABLE_NAME,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        	GoalBean goal = cursorToGoal(cursor);
        	goals.add(goal);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return goals;
    }
    
    public GoalBean updateGoal(GoalBean goalBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Goal.COLUMN_GOAL, goalBeanObj.getGoal());
        values.put(Goal.COLUMN_AMT, goalBeanObj.getAmt());
        values.put(Goal.COLUMN_DDL, goalBeanObj.getDdl().toString());
        values.put(Goal.COLUMN_STS, goalBeanObj.getSts());
        values.put(Goal.COLUMN_ADAT, goalBeanObj.getAdat().toString());
        String[] whereArgs = {goalBeanObj.getId().toString()} ;
        
        long updateId = database.update(Goal.TABLE_NAME, values, Goal.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Goal.TABLE_NAME,
                        allColumns, Goal.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
        cursor.moveToFirst();
        goalBeanObj = cursorToGoal(cursor);
        cursor.close();
        return goalBeanObj;
    }

    private GoalBean cursorToGoal(Cursor cursor) {
    	GoalBean goal = new GoalBean();
    	goal.setId(cursor.getLong(0));
    	goal.setGoal(cursor.getString(1));
    	goal.setAmt(cursor.getLong(2));
    	goal.setDdl(Date.valueOf(cursor.getString(3)));
    	goal.setSts(cursor.getString(4));
    	goal.setAdat(Date.valueOf(cursor.getString(5)));
        return goal;
    }
}
