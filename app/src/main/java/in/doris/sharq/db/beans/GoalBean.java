package in.doris.sharq.db.beans;

import java.sql.Date;

public class GoalBean {
	private Long id = null;
    private String goal;
    private Long amt= 0L;//Amount
    private Date ddl = null;//Deadline Date.
    private String sts;//Status.
    private Date adat= null;//Achieved Date.


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getGoal() {
		return goal;
	}
	public void setGoal(String goal) {
		this.goal = goal;
	}
	public Long getAmt() {
		return amt;
	}
	public void setAmt(Long amt) {
		this.amt = amt;
	}
	public Date getDdl() {return ddl;}
	public void setDdl(Date ddl) {
		this.ddl = ddl;
	}
	public String getSts() {
		return sts;
	}
	public void setSts(String sts) {
		this.sts = sts;
	}
	public Date getAdat() {
		return adat;
	}
	public void setAdat(Date adat) {
		this.adat = adat;
	}
    
	// Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        //return the text required for ArrayAdapter after checking the format.    
    	//return ArrayAdapter readable string;
    	return null;
    }
    
}
