package in.doris.sharq.db.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import in.doris.sharq.db.constants.DbConstants;
import in.doris.sharq.db.contracts.DbContract.*;

public class SharqDbHelper extends SQLiteOpenHelper {
	// ----------------------------------------------------------------------------
	private static SharqDbHelper instance = null;

	private SharqDbHelper(Context context) {// Exists only to defeat
											// instantiation.
		super(context, DbConstants.DATABASE_NAME, null,
				DbConstants.DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	public static SharqDbHelper getInstance(Context context) {
		if (instance == null) {
			instance = new SharqDbHelper(context);
		}
		return instance;
	}

	// ----------------------------------------------------------------------------
	
	private static final String SQL_CREATE_MSTUSER = "create table "
			+ MstUser.TABLE_NAME + " (" 
			+ MstUser._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT + ","
			+ MstUser.COLUMN_IR + DbConstants.TEXT + "," 
			+ MstUser.COLUMN_NAME + DbConstants.TEXT + "," 
			+ MstUser.COLUMN_PHONE + DbConstants.INTEGER + ","
			+ MstUser.COLUMN_EMAIL + DbConstants.TEXT + ","
			+ MstUser.COLUMN_DATJND + DbConstants.TEXT + ","
			+ MstUser.COLUMN_LNAME + DbConstants.TEXT + ","
			+ MstUser.COLUMN_RANK + DbConstants.TEXT + ","
			+ MstUser.COLUMN_CHECKLIST + DbConstants.TEXT + ","
			+ MstUser.COLUMN_CCY + DbConstants.TEXT + " )";

	private static final String SQL_DELETE_MSTUSER = "drop table if exists "
			+ MstUser.TABLE_NAME;
	
	// ----------------------------------------------------------------------------
	
	private static final String SQL_CREATE_BLUEPRINT = "create table "
			+ Blueprint.TABLE_NAME + " (" 
			+ Blueprint._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT+ ","
			+ Blueprint.COLUMN_BLUEPRINT + DbConstants.TEXT + ","
			+ Blueprint.COLUMN_STS + DbConstants.TEXT + ","
			+ Blueprint.COLUMN_ADAT + DbConstants.TEXT + " )";

	private static final String SQL_DELETE_BLUEPRINT = "drop table if exists "
			+ Blueprint.TABLE_NAME;
	
	// ----------------------------------------------------------------------------
	
	private static final String SQL_CREATE_GOAL = "create table "
			+ Goal.TABLE_NAME + " (" 
			+ Goal._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT+ ","
			+ Goal.COLUMN_GOAL + DbConstants.TEXT + ","
			+ Goal.COLUMN_AMT + DbConstants.INTEGER + ","
			+ Goal.COLUMN_DDL + DbConstants.TEXT + ","
			+ Goal.COLUMN_STS + DbConstants.TEXT + ","
			+ Goal.COLUMN_ADAT + DbConstants.TEXT + " )";

	private static final String SQL_DELETE_GOAL = "drop table if exists "
			+ Goal.TABLE_NAME;
	
	// ----------------------------------------------------------------------------
	
	private static final String SQL_CREATE_NAMELIST = "create table "
			+ Namelist.TABLE_NAME + " (" 
			+ Namelist._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT+ ","
			+ Namelist.COLUMN_NAME + DbConstants.TEXT + ","
			+ Namelist.COLUMN_PHONE + DbConstants.INTEGER + ","
			+ Namelist.COLUMN_PLACE + DbConstants.TEXT + ","
			+ Namelist.COLUMN_CAT + DbConstants.TEXT + ","
			+ Namelist.COLUMN_STS + DbConstants.TEXT + ","
			+ Namelist.COLUMN_REMARK + DbConstants.TEXT + ","
			+ Namelist.COLUMN_STAR + DbConstants.TEXT + " )";

	private static final String SQL_DELETE_NAMELIST = "drop table if exists "
			+ Namelist.TABLE_NAME;
	
	// ----------------------------------------------------------------------------

	private static final String SQL_CREATE_FOLLOWUP = "create table "
			+ Followup.TABLE_NAME + " ("
			+ Followup._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT+ ","
			+ Followup.COLUMN_NAME + DbConstants.TEXT + ","
			+ Followup.COLUMN_PHONE + DbConstants.INTEGER + ","
			+ Followup.COLUMN_PLACE + DbConstants.TEXT + ","
			+ Followup.COLUMN_RNAME + DbConstants.TEXT + ","
			+ Followup.COLUMN_DATE + DbConstants.TEXT + ","
			+ Followup.COLUMN_LCON + DbConstants.TEXT + ","
			+ Followup.COLUMN_REMARK + DbConstants.TEXT + ","
			+ Followup.COLUMN_CSTS + DbConstants.TEXT + ","
			+ Followup.COLUMN_SDATE + DbConstants.TEXT + ","
			+ Followup.COLUMN_BV + DbConstants.INTEGER + ","
			+ Followup.COLUMN_LR + DbConstants.TEXT + ","
			+ Followup.COLUMN_DIRECT + DbConstants.TEXT + ","
			+ Followup.COLUMN_NOF + DbConstants.INTEGER + " )";

	private static final String SQL_DELETE_FOLLOWUP = "drop table if exists "
			+ Followup.TABLE_NAME;

	// ----------------------------------------------------------------------------

    private static final String SQL_CREATE_FDETAILS = "create table "
            + FDetails.TABLE_NAME + " ("
            + FDetails.COLUMN_FID + DbConstants.INTEGER + ","
            + FDetails.COLUMN_FNO + DbConstants.INTEGER + ","
            + FDetails.COLUMN_DATE + DbConstants.TEXT + ","
            + FDetails.COLUMN_REMARK + DbConstants.TEXT + " )";

    private static final String SQL_DELETE_FDETAILS = "drop table if exists "
            + FDetails.TABLE_NAME;

    // ----------------------------------------------------------------------------

	private static final String SQL_CREATE_SUCCESS = "create table "
			+ Success.TABLE_NAME + " (" 
			+ Success._ID + DbConstants.INTEGER
			+ DbConstants.PK + DbConstants.AUTOINCREMENT+ ","
			+ Success.COLUMN_YEAR + DbConstants.INTEGER + ","
			+ Success.COLUMN_WEEK + DbConstants.INTEGER + ","
			+ Success.COLUMN_NOP + DbConstants.INTEGER + ","
			+ Success.COLUMN_SN + DbConstants.INTEGER + ","
			+ Success.COLUMN_SBV + DbConstants.INTEGER + ","
			+ Success.COLUMN_LN + DbConstants.INTEGER + ","
			+ Success.COLUMN_LBV + DbConstants.INTEGER + ","
			+ Success.COLUMN_RN + DbConstants.INTEGER + ","
			+ Success.COLUMN_RBV + DbConstants.INTEGER + ","
			+ Success.COLUMN_CYCLE + DbConstants.INTEGER + ","
			+ Success.COLUMN_STEP + DbConstants.INTEGER + ","
			+ Success.COLUMN_INCOME + DbConstants.INTEGER + " )";

	private static final String SQL_DELETE_SUCCESS = "drop table if exists "
			+ Success.TABLE_NAME;
	
	// ----------------------------------------------------------------------------

	
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(SQL_CREATE_MSTUSER);
		db.execSQL(SQL_CREATE_BLUEPRINT);
		db.execSQL(SQL_CREATE_GOAL);
		db.execSQL(SQL_CREATE_NAMELIST);
		db.execSQL(SQL_CREATE_FOLLOWUP);
        db.execSQL(SQL_CREATE_FDETAILS);
		db.execSQL(SQL_CREATE_SUCCESS);
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// This database is only a cache for online data, so its upgrade policy
		// is
		// to simply to discard the data and start over
		Log.w(SharqDbHelper.class.getName(),
				"Upgrading database from version " + oldVersion + " to "
						+ newVersion + ", which will destroy all old data");
        db.execSQL(SQL_DELETE_MSTUSER);
		db.execSQL(SQL_DELETE_BLUEPRINT);
		db.execSQL(SQL_DELETE_GOAL);
		db.execSQL(SQL_DELETE_NAMELIST);
		db.execSQL(SQL_DELETE_FOLLOWUP);
        db.execSQL(SQL_DELETE_FDETAILS);
		db.execSQL(SQL_DELETE_SUCCESS);
		onCreate(db);
	}

	public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		onUpgrade(db, oldVersion, newVersion);
	}
	// ----------------------------------------------------------------------------

}
