package in.doris.sharq.db.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import in.doris.sharq.db.beans.FDetailsBean;
import in.doris.sharq.db.contracts.DbContract.FDetails;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

/**
 * Created by Nishu on 04-10-2016.
 */
public class FDetailsDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = {
            FDetails.COLUMN_FID,
            FDetails.COLUMN_FNO,
            FDetails.COLUMN_DATE,
            FDetails.COLUMN_REMARK
    };

    public FDetailsDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public FDetailsBean createFDetails(FDetailsBean fDetailsBeanObj) {
        ContentValues values = new ContentValues();
        values.put(FDetails.COLUMN_FID, fDetailsBeanObj.getfId());
        values.put(FDetails.COLUMN_FNO, fDetailsBeanObj.getfNo());
        values.put(FDetails.COLUMN_DATE, fDetailsBeanObj.getDate().toString());
        values.put(FDetails.COLUMN_REMARK, fDetailsBeanObj.getRemark());
        long insertId = database.insert(FDetails.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(
                FDetails.TABLE_NAME,
                allColumns,
                FDetails.COLUMN_FID + " = " + fDetailsBeanObj.getfId()
                        + " and " + FDetails.COLUMN_FNO + " = " + fDetailsBeanObj.getfNo()
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        FDetailsBean newFDetails = cursorToFDetails(cursor);
        cursor.close();
        return newFDetails;
    }

    public void deleteFDetails(FDetailsBean fDetailsBeanObj) {
        long fid = fDetailsBeanObj.getfId();
        long fno = fDetailsBeanObj.getfNo();
        System.out.println("FDetails deletion initiated with fid: " + fid + "and fno: " + fno);
        database.delete(
                FDetails.TABLE_NAME,
                FDetails.COLUMN_FID + " = " + " and " + FDetails.COLUMN_FNO + " = " + fno
                , null);
        System.out.println("FDetails deleted with fid: " + fid + "and fno: " + fno);
    }

    public List<FDetailsBean> getAllFDetails(FDetailsBean fDetailsBeanObj) {
        String[] queryArgs = {String.valueOf(fDetailsBeanObj.getfId()),String.valueOf(fDetailsBeanObj.getfNo())};

        List<FDetailsBean> fDetails = new ArrayList<FDetailsBean>();

        Cursor cursor = database.query(
                FDetails.TABLE_NAME,
                allColumns,
                "where " + FDetails.COLUMN_FID + " = ? and " + FDetails.COLUMN_FNO + " = ? ",
                queryArgs,
                null,
                null,
                String.valueOf(fDetailsBeanObj.getfNo()));

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FDetailsBean fDetail = cursorToFDetails(cursor);
            fDetails.add(fDetail);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return fDetails;
    }

    public FDetailsBean updateFDetails(FDetailsBean fDetailsBeanObj) {
        ContentValues values = new ContentValues();
        values.put(FDetails.COLUMN_FID, fDetailsBeanObj.getfId());
        values.put(FDetails.COLUMN_FNO, fDetailsBeanObj.getfNo());
        values.put(FDetails.COLUMN_DATE, fDetailsBeanObj.getDate().toString());
        values.put(FDetails.COLUMN_REMARK, fDetailsBeanObj.getRemark());
        String[] whereArgs = {String.valueOf(fDetailsBeanObj.getfId()),String.valueOf(fDetailsBeanObj.getfNo())};

        long updateId = database.update(FDetails.TABLE_NAME, values,
                "where " + FDetails.COLUMN_FID + " = ? and " + FDetails.COLUMN_FNO + " = ? ",
                whereArgs);
        Cursor cursor = database.query(
                FDetails.TABLE_NAME,
                allColumns,
                FDetails.COLUMN_FID + " = " + fDetailsBeanObj.getfId()
                        + " and " + FDetails.COLUMN_FNO + " = " + fDetailsBeanObj.getfNo()
                , null
                , null
                , null
                , null);
        cursor.moveToFirst();
        fDetailsBeanObj = cursorToFDetails(cursor);
        cursor.close();
        return fDetailsBeanObj;
    }

    private FDetailsBean cursorToFDetails(Cursor cursor) {
        FDetailsBean fDetails = new FDetailsBean();
        fDetails.setfId(cursor.getLong(0));
        fDetails.setfNo(cursor.getLong(1));
        fDetails.setDate(Date.valueOf(cursor.getString(2)));
        fDetails.setRemark(cursor.getString(3));
        return fDetails;
    }
}
