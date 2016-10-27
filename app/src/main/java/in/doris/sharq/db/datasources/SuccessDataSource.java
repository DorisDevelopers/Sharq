package in.doris.sharq.db.datasources;


import in.doris.sharq.db.beans.SuccessBean;
import in.doris.sharq.db.contracts.DbContract.Success;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class SuccessDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = { 
    		Success.COLUMN_YEAR,
            Success.COLUMN_WEEK,
            Success.COLUMN_NOP, 
            Success.COLUMN_SN,
            Success.COLUMN_SBV,
            Success.COLUMN_LN,
            Success.COLUMN_LBV,
            Success.COLUMN_RN,
            Success.COLUMN_RBV,
            Success.COLUMN_CYCLE,
            Success.COLUMN_STEP,
            Success.COLUMN_INCOME
    };

    public SuccessDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public SuccessBean createSuccess(SuccessBean successBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Success.COLUMN_YEAR, successBeanObj.getYear());
        values.put(Success.COLUMN_WEEK, successBeanObj.getWeek());
        values.put(Success.COLUMN_NOP, successBeanObj.getNop());
        values.put(Success.COLUMN_SN, successBeanObj.getSn());
        values.put(Success.COLUMN_SBV, successBeanObj.getSbv());
        values.put(Success.COLUMN_LN, successBeanObj.getLn());
        values.put(Success.COLUMN_LBV, successBeanObj.getLbv());
        values.put(Success.COLUMN_RN, successBeanObj.getRn());
        values.put(Success.COLUMN_RBV, successBeanObj.getRbv());
        values.put(Success.COLUMN_CYCLE, successBeanObj.getCycle());
        values.put(Success.COLUMN_STEP, successBeanObj.getStep());
        values.put(Success.COLUMN_INCOME, successBeanObj.getIncome());
        long insertId = database.insert(Success.TABLE_NAME, null,
                        values);
        Cursor cursor = database.query(Success.TABLE_NAME,
                        allColumns, Success.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
        cursor.moveToFirst();
        SuccessBean newSuccess = cursorToSuccess(cursor);
        cursor.close();
        return newSuccess;
    }

    public void deleteSuccess(SuccessBean successBeanObj) {
        long id = successBeanObj.getId();
        System.out.println("Success deletion initiated with id: " + id);
        database.delete(Success.TABLE_NAME, Success.COLUMN_ID
                + " = " + id, null);
        System.out.println("Success deleted with id: " + id);
    }

    public List<SuccessBean> getAllUsers() {
        List<SuccessBean> successes = new ArrayList<SuccessBean>();

        Cursor cursor = database.query(Success.TABLE_NAME,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        		SuccessBean success = cursorToSuccess(cursor);
        		successes.add(success);
                cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return successes;
    }
    
    public SuccessBean updateSuccess (SuccessBean successBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Success.COLUMN_YEAR, successBeanObj.getYear());
        values.put(Success.COLUMN_WEEK, successBeanObj.getWeek());
        values.put(Success.COLUMN_NOP, successBeanObj.getNop());
        values.put(Success.COLUMN_SN, successBeanObj.getSn());
        values.put(Success.COLUMN_SBV, successBeanObj.getSbv());
        values.put(Success.COLUMN_LN, successBeanObj.getLn());
        values.put(Success.COLUMN_LBV, successBeanObj.getLbv());
        values.put(Success.COLUMN_RN, successBeanObj.getRn());
        values.put(Success.COLUMN_RBV, successBeanObj.getRbv());
        values.put(Success.COLUMN_CYCLE, successBeanObj.getCycle());
        values.put(Success.COLUMN_STEP, successBeanObj.getStep());
        values.put(Success.COLUMN_INCOME, successBeanObj.getIncome());
        String[] whereArgs = {successBeanObj.getId().toString()} ;
        
        long updateId = database.update(Success.TABLE_NAME, values, Success.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Success.TABLE_NAME,
                        allColumns, Success.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
        cursor.moveToFirst();
        successBeanObj = cursorToSuccess(cursor);
        cursor.close();
        return successBeanObj;
    }

    private SuccessBean cursorToSuccess(Cursor cursor) {
    	SuccessBean success = new SuccessBean();
    	success.setId(cursor.getLong(0));
    	success.setYear(cursor.getLong(1));
    	success.setWeek(cursor.getLong(2));
    	success.setNop(cursor.getLong(3));
    	success.setSn(cursor.getLong(4));
    	success.setSbv(cursor.getLong(5));
    	success.setLn(cursor.getLong(6));
    	success.setLbv(cursor.getLong(7));
    	success.setRn(cursor.getLong(8));
    	success.setRbv(cursor.getLong(9));
    	success.setCycle(cursor.getLong(10));
    	success.setStep(cursor.getLong(11));
    	success.setIncome(cursor.getLong(12));
        return success;
    }

}
