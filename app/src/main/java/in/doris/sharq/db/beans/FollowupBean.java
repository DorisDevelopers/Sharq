package in.doris.sharq.db.beans;

import java.sql.Date;
import java.util.List;

/**
 * Created by Nishu on 03-10-2016.
 */
public class FollowupBean {
    private Long id;
    private String name;
    private Long phone;
    private String place;
    private String rname;//referrer name.
    private Date date;//Date of Presentation.
    private String lcon;//Last conversation.
    private String remark;
    private String csts;//Current status.
    private Date sdate;//Date of Signup.
    private Long bv;//No. of BV.
    private String lr;//Left or Right.
    private String direct;
    private long nof;//No of followups.


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getLcon() {
        return lcon;
    }

    public void setLcon(String lcon) {
        this.lcon = lcon;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCsts() {
        return csts;
    }

    public void setCsts(String csts) {
        this.csts = csts;
    }

    public Date getSdate() {
        return sdate;
    }

    public void setSdate(Date sdate) {
        this.sdate = sdate;
    }

    public Long getBv() {
        return bv;
    }

    public void setBv(Long bv) {
        this.bv = bv;
    }

    public String getLr() {
        return lr;
    }

    public void setLr(String lr) {
        this.lr = lr;
    }

    public String getDirect() {
        return direct;
    }

    public void setDirect(String direct) {
        this.direct = direct;
    }

    public long getNof() {
        return nof;
    }

    public void setNof(long nof) {
        this.nof = nof;
    }

    // Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        //return the text required for ArrayAdapter after checking the format.
        //return ArrayAdapter readable string;
        return null;
    }
}
