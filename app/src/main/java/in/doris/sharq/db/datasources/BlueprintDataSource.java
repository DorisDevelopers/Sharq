package in.doris.sharq.db.datasources;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import in.doris.sharq.db.beans.BlueprintBean;
import in.doris.sharq.db.contracts.DbContract.Blueprint;
import in.doris.sharq.db.dbHelper.SharqDbHelper;


public class BlueprintDataSource {
	// Database fields
    private SQLiteDatabase database;
    private SharqDbHelper dbHelper;
    private String[] allColumns = { 
    		Blueprint.COLUMN_ID,
    		Blueprint.COLUMN_BLUEPRINT,
    		Blueprint.COLUMN_STS,
    		Blueprint.COLUMN_ADAT
    };
    
    public BlueprintDataSource(Context context) {
        dbHelper = SharqDbHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }
    
    public BlueprintBean createBlueprint(BlueprintBean blueprintBeanObj) {
        ContentValues values = new ContentValues();
        open();
        values.put(Blueprint.COLUMN_BLUEPRINT, blueprintBeanObj.getBlueprint());
        values.put(Blueprint.COLUMN_STS, blueprintBeanObj.getSts());
        values.put(Blueprint.COLUMN_ADAT, blueprintBeanObj.getAdat().toString());
        long insertId = 0L;
        insertId=database.insert(Blueprint.TABLE_NAME, null,
                        values);
        Cursor cursor = database.query(Blueprint.TABLE_NAME,
                        allColumns, Blueprint.COLUMN_ID + " = " + insertId, null,
                        null, null, null);
        cursor.moveToFirst();
        BlueprintBean newBlueprint = cursorToBlueprint(cursor);
        cursor.close();
        return newBlueprint;
    }

    public void deleteUser(BlueprintBean blueprintBeanObj) {
        long id = blueprintBeanObj.getId();
        System.out.println("Blueprint deletion initiated with id: " + id);
        database.delete(Blueprint.TABLE_NAME, Blueprint.COLUMN_ID
                + " = " + id, null);
        System.out.println("Blueprint deleted with id: " + id);
    }

    public List<BlueprintBean> getAllBlueprints() {
        List<BlueprintBean> blueprints = new ArrayList<BlueprintBean>();

        Cursor cursor = database.query(Blueprint.TABLE_NAME,
                        allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
        		BlueprintBean blueprint = cursorToBlueprint(cursor);
        		blueprints.add(blueprint);
                cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return blueprints;
    }
    
    public BlueprintBean updateBlueprint(BlueprintBean blueprintBeanObj) {
    	ContentValues values = new ContentValues();
        open();
        values.put(Blueprint.COLUMN_BLUEPRINT, blueprintBeanObj.getBlueprint());
        values.put(Blueprint.COLUMN_STS, blueprintBeanObj.getSts());
        values.put(Blueprint.COLUMN_ADAT, blueprintBeanObj.getAdat().toString());
        String[] whereArgs = {blueprintBeanObj.getId().toString()} ;
        
        long updateId = database.update(Blueprint.TABLE_NAME, values, Blueprint.COLUMN_ID + " = ?", whereArgs);
        Cursor cursor = database.query(Blueprint.TABLE_NAME,
                        allColumns, Blueprint.COLUMN_ID + " = " + updateId, null,
                        null, null, null);
        cursor.moveToFirst();
        blueprintBeanObj = cursorToBlueprint(cursor);
        cursor.close();
        return blueprintBeanObj;
    }

    private BlueprintBean cursorToBlueprint(Cursor cursor) {
    	BlueprintBean blueprint = new BlueprintBean();
    	blueprint.setId(cursor.getLong(0));
    	blueprint.setBlueprint(cursor.getString(1));
    	blueprint.setSts(cursor.getString(2));
        blueprint.setAdat(Date.valueOf(cursor.getString(3)));
        return blueprint;
    }

}
