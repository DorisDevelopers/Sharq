package in.doris.sharq.db.contracts;

import android.provider.BaseColumns;

public final class DbContract {
    // To prevent someone from accidentally instantiating the contract class,
    // make the constructor private.
    private DbContract() {}
    
 // ----------------------------------------------------------------------------
    
    /* Inner class that defines the table contents */
    public static class MstUser implements BaseColumns {
        public static final String TABLE_NAME = "mstuser";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_IR = "ir";//IR Id.
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_EMAIL = "email";
        public static final String COLUMN_DATJND = "datjnd";//Date Joined.
        public static final String COLUMN_LNAME = "lname";//Leader's Name.
        public static final String COLUMN_RANK = "rank";
        public static final String COLUMN_CHECKLIST = "checklist";
        public static final String COLUMN_CCY = "ccy";//Currency.
    }
    
 // ----------------------------------------------------------------------------  
    
    /* Inner class that defines the table contents */
    public static class Blueprint implements BaseColumns {
        public static final String TABLE_NAME = "blueprint";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_BLUEPRINT = "blueprint";
        public static final String COLUMN_STS = "sts";//Status.
        public static final String COLUMN_ADAT = "adat";//Achieved Date.
    }
    
 // ----------------------------------------------------------------------------
    
    /* Inner class that defines the table contents */
    public static class Goal implements BaseColumns {
        public static final String TABLE_NAME = "activity_goals";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_GOAL = "goal";
        public static final String COLUMN_AMT = "amt";//Amount.
        public static final String COLUMN_DDL = "ddl";//Deadline Date.
        public static final String COLUMN_STS = "sts";//Status.
        public static final String COLUMN_ADAT = "adat";//Achieved Date.
    }
// ----------------------------------------------------------------------------
    
    /* Inner class that defines the table contents */
    public static class Namelist implements BaseColumns {
        public static final String TABLE_NAME = "namelist";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_CAT = "cat";//Catagory.
        public static final String COLUMN_STS = "sts";//Status.
        public static final String COLUMN_REMARK = "remark";
        public static final String COLUMN_STAR = "star";//Star Marked Flag.
    }
       
 // ----------------------------------------------------------------------------

    /* Inner class that defines the table contents */
    public static class Followup implements BaseColumns {
        public static final String TABLE_NAME = "followup";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PHONE = "phone";
        public static final String COLUMN_PLACE = "place";
        public static final String COLUMN_RNAME = "rname";//referrer name.
        public static final String COLUMN_DATE = "date";//Date of Presentation.
        public static final String COLUMN_LCON = "lcon";//Last conversation.
        public static final String COLUMN_REMARK = "remark";
        public static final String COLUMN_CSTS = "csts";//Current status.
        public static final String COLUMN_SDATE = "sdate";//Date of Signup.
        public static final String COLUMN_BV = "bv";//No. of BV.
        public static final String COLUMN_LR = "lr";//Left or Right.
        public static final String COLUMN_DIRECT = "direct";
        public static final String COLUMN_NOF = "nof";//No of followups.
    }

    // ----------------------------------------------------------------------------

    /* Inner class that defines the table contents */
    public static class FDetails implements BaseColumns {
        public static final String TABLE_NAME = "fdetails";
        public static final String COLUMN_FID = "fid";
        public static final String COLUMN_FNO = "fno";
        public static final String COLUMN_DATE = "date";//Date of Followup.
        public static final String COLUMN_REMARK = "remark";
    }

    // ----------------------------------------------------------------------------

    /* Inner class that defines the table contents */
    public static class Success implements BaseColumns {
        public static final String TABLE_NAME = "success";
        public static final String COLUMN_ID = "_id";
        public static final String COLUMN_YEAR = "year";
        public static final String COLUMN_WEEK = "week";
        public static final String COLUMN_NOP = "nop";//No of presentations.
        public static final String COLUMN_SN = "sn";//No. of signup.
        public static final String COLUMN_SBV = "sbv";//No. BV signup.
        public static final String COLUMN_LN = "ln";//Signup on left.
        public static final String COLUMN_LBV = "lbv";//BV on left.
        public static final String COLUMN_RN = "rn";//Signup on right.
        public static final String COLUMN_RBV = "rbv";//BV on right.
        public static final String COLUMN_CYCLE = "cycle";
        public static final String COLUMN_STEP = "step";
        public static final String COLUMN_INCOME = "income";
    }
       
 // ----------------------------------------------------------------------------

}
