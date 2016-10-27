package in.doris.sharq.db.datasources;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import in.doris.sharq.db.beans.FollowupBean;
import in.doris.sharq.db.contracts.DbContract.Followup;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

/**
 * Created by Nishu on 03-10-2016.
 */
public class FollowupDataSource {
    // Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = {
            Followup.COLUMN_ID,
            Followup.COLUMN_NAME,
            Followup.COLUMN_PHONE,
            Followup.COLUMN_PLACE,
            Followup.COLUMN_RNAME,
            Followup.COLUMN_DATE,
            Followup.COLUMN_LCON,
            Followup.COLUMN_REMARK,
            Followup.COLUMN_CSTS,
            Followup.COLUMN_SDATE,
            Followup.COLUMN_BV,
            Followup.COLUMN_LR,
            Followup.COLUMN_DIRECT,
            Followup.COLUMN_NOF
    };

    public FollowupDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public FollowupBean createFollowup(FollowupBean followupBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Followup.COLUMN_NAME, followupBeanObj.getName());
        values.put(Followup.COLUMN_PHONE, followupBeanObj.getPhone());
        values.put(Followup.COLUMN_PLACE, followupBeanObj.getPlace());
        values.put(Followup.COLUMN_RNAME, followupBeanObj.getRname());
        values.put(Followup.COLUMN_DATE, followupBeanObj.getDate().toString());
        values.put(Followup.COLUMN_LCON, followupBeanObj.getLcon());
        values.put(Followup.COLUMN_REMARK, followupBeanObj.getRemark());
        values.put(Followup.COLUMN_CSTS, followupBeanObj.getCsts());
        values.put(Followup.COLUMN_SDATE, followupBeanObj.getSdate().toString());
        values.put(Followup.COLUMN_BV, followupBeanObj.getBv());
        values.put(Followup.COLUMN_LR, followupBeanObj.getLr());
        values.put(Followup.COLUMN_DIRECT, followupBeanObj.getDirect());
        values.put(Followup.COLUMN_NOF, followupBeanObj.getNof());
        long insertId = database.insert(Followup.TABLE_NAME, null,
                values);
        Cursor cursor = database.query(Followup.TABLE_NAME,
                allColumns, Followup.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        FollowupBean newFollowup = cursorToFollowup(cursor);
        cursor.close();
        return newFollowup;
    }

    public void deleteFollowup(FollowupBean followupBeanObj) {
        long id = followupBeanObj.getId();
        System.out.println("Followup deletion initiated with id: " + id);
        database.delete(Followup.TABLE_NAME, Followup.COLUMN_ID
                + " = " + id, null);
        System.out.println("Followup deleted with id: " + id);
    }

    public List<FollowupBean> getAllFollowups() {
        List<FollowupBean> followups = new ArrayList<FollowupBean>();

        Cursor cursor = database.query(Followup.TABLE_NAME,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            FollowupBean followup = cursorToFollowup(cursor);
            followups.add(followup);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return followups;
    }

    public FollowupBean updateFollowup(FollowupBean followupBeanObj) {
        ContentValues values = new ContentValues();
        values.put(Followup.COLUMN_NAME, followupBeanObj.getName());
        values.put(Followup.COLUMN_PHONE, followupBeanObj.getPhone());
        values.put(Followup.COLUMN_PLACE, followupBeanObj.getPlace());
        values.put(Followup.COLUMN_RNAME, followupBeanObj.getRname());
        values.put(Followup.COLUMN_DATE, followupBeanObj.getDate().toString());
        values.put(Followup.COLUMN_LCON, followupBeanObj.getLcon());
        values.put(Followup.COLUMN_REMARK, followupBeanObj.getRemark());
        values.put(Followup.COLUMN_CSTS, followupBeanObj.getCsts());
        values.put(Followup.COLUMN_SDATE, followupBeanObj.getSdate().toString());
        values.put(Followup.COLUMN_BV, followupBeanObj.getBv());
        values.put(Followup.COLUMN_LR, followupBeanObj.getLr());
        values.put(Followup.COLUMN_DIRECT, followupBeanObj.getDirect());
        values.put(Followup.COLUMN_NOF, followupBeanObj.getNof());
        String[] whereArgs = {followupBeanObj.getId().toString()} ;

        long updateId = database.update(Followup.TABLE_NAME, values, Followup.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Followup.TABLE_NAME,
                allColumns, Followup.COLUMN_ID + " = " + updateId, null,
                null, null, null);
        cursor.moveToFirst();
        followupBeanObj = cursorToFollowup(cursor);
        cursor.close();
        return followupBeanObj;
    }

    private FollowupBean cursorToFollowup(Cursor cursor) {
        FollowupBean followup = new FollowupBean();
        followup.setId(cursor.getLong(0));
        followup.setName(cursor.getString(1));
        followup.setPhone(cursor.getLong(2));
        followup.setPlace(cursor.getString(3));
        followup.setRname(cursor.getString(4));
        followup.setDate(Date.valueOf(cursor.getString(5)));
        followup.setLcon(cursor.getString(6));
        followup.setRemark(cursor.getString(7));
        followup.setCsts(cursor.getString(8));
        followup.setSdate(Date.valueOf(cursor.getString(9)));
        followup.setBv(cursor.getLong(10));
        followup.setLr(cursor.getString(11));
        followup.setDirect(cursor.getString(12));
        followup.setNof(cursor.getLong(13));
        return followup;
    }
}
