package in.doris.sharq.db.beans;

import java.util.Date;

/**
 * Created by Nishu on 03-10-2016.
 */
public class FDetailsBean {
    private long fId;//Followup Id, foreign key to Followup table.
    private long fNo;//Followup No.
    private Date date;//Date of Followup
    private String remark;

    public long getfId() {
        return fId;
    }

    public void setfId(long fId) {
        this.fId = fId;
    }

    public long getfNo() {
        return fNo;
    }

    public void setfNo(long fNo) {
        this.fNo = fNo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        //return the text required for ArrayAdapter after checking the format.
        //return ArrayAdapter readable string;
        return null;
    }
}
