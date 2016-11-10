package in.doris.sharq.db.datasources;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import in.doris.sharq.db.beans.MstUserBean;
import in.doris.sharq.db.contracts.DbContract.MstUser;
import in.doris.sharq.db.dbHelper.SharqDbHelper;

public class MstUserDataSource {

    // Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = { 
    	MstUser.COLUMN_ID,
        MstUser.COLUMN_IR,
        MstUser.COLUMN_NAME,
        MstUser.COLUMN_PHONE,
        MstUser.COLUMN_EMAIL,
        MstUser.COLUMN_DATJND,
        MstUser.COLUMN_LNAME,
        MstUser.COLUMN_RANK,
        MstUser.COLUMN_CHECKLIST,
        MstUser.COLUMN_CCY
    };


    public MstUserDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public MstUserBean createUser(MstUserBean mstUserBeanObj) {
        ContentValues values = new ContentValues();
        values.put(MstUser.COLUMN_IR, mstUserBeanObj.getIr());
        values.put(MstUser.COLUMN_NAME, mstUserBeanObj.getName());
        values.put(MstUser.COLUMN_PHONE, mstUserBeanObj.getPhone());
        values.put(MstUser.COLUMN_EMAIL, mstUserBeanObj.getEmail());
        if(null !=  mstUserBeanObj.getDatjnd()) {
            values.put(MstUser.COLUMN_DATJND, mstUserBeanObj.getDatjnd().toString());
        }
        values.put(MstUser.COLUMN_LNAME, mstUserBeanObj.getLname());
        values.put(MstUser.COLUMN_RANK, mstUserBeanObj.getRank());
        values.put(MstUser.COLUMN_CHECKLIST, mstUserBeanObj.getChecklist());
        values.put(MstUser.COLUMN_CCY, mstUserBeanObj.getCcy());
        long insertId = database.insert(MstUser.TABLE_NAME, null,
                        values);
        Cursor cursor = database.query(MstUser.TABLE_NAME,
                        allColumns, MstUser.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
        cursor.moveToFirst();
        MstUserBean newUser = cursorToUser(cursor);
        cursor.close();
        return newUser;
    }

    public void deleteUser(MstUserBean mstUserBeanObj) {
        String[] id = {mstUserBeanObj.getId().toString()};
        System.out.println("User deletion initiated with id: " + id);
        database.delete(MstUser.TABLE_NAME, MstUser.COLUMN_ID
                + " = ? ", id);
        System.out.println("User deleted with id: " + id);
    }

    public List<MstUserBean> getAllUsers() {
        List<MstUserBean> users = new ArrayList<MstUserBean>();

        Cursor cursor = database.query(MstUser.TABLE_NAME,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
                MstUserBean user = cursorToUser(cursor);
                users.add(user);
                cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return users;
    }
    
    public MstUserBean updateUser(MstUserBean mstUserBeanObj) {
        ContentValues values = new ContentValues();
        values.put(MstUser.COLUMN_IR, mstUserBeanObj.getIr());
        values.put(MstUser.COLUMN_NAME, mstUserBeanObj.getName());
        values.put(MstUser.COLUMN_PHONE, mstUserBeanObj.getPhone());
        values.put(MstUser.COLUMN_EMAIL, mstUserBeanObj.getEmail());
        values.put(MstUser.COLUMN_DATJND, mstUserBeanObj.getDatjnd().toString());
        values.put(MstUser.COLUMN_LNAME, mstUserBeanObj.getLname());
        values.put(MstUser.COLUMN_RANK, mstUserBeanObj.getRank());
        values.put(MstUser.COLUMN_CHECKLIST, mstUserBeanObj.getChecklist());
        values.put(MstUser.COLUMN_CCY, mstUserBeanObj.getCcy());
        String[] whereArgs = {mstUserBeanObj.getId().toString()} ;
        
        long updateId = database.update(MstUser.TABLE_NAME, values, MstUser.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(MstUser.TABLE_NAME,
                        allColumns, MstUser.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
        cursor.moveToFirst();
        mstUserBeanObj = cursorToUser(cursor);
        cursor.close();
        return mstUserBeanObj;
    }

    public int deleteAllUsers(){
        return database.delete(MstUser.TABLE_NAME, null, null);
    }

    private MstUserBean cursorToUser(Cursor cursor) {
        MstUserBean user = new MstUserBean();
        user.setId(cursor.getLong(0));
        user.setIr(cursor.getString(1));
        user.setName(cursor.getString(2));
        user.setPhone(cursor.getLong(3));
        user.setEmail(cursor.getString(4));
        user.setDatjnd(null!=cursor.getString(5)?Date.valueOf(cursor.getString(5)):null);
        user.setLname(cursor.getString(6));
        user.setRank(cursor.getString(7));
        user.setChecklist(cursor.getString(8));
        user.setCcy(cursor.getString(9));
        return user;
    }
}
