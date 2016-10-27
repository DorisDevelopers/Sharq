package in.doris.sharq.db.beans;

import java.sql.Date;

public class BlueprintBean {

	private Long id;
    private String blueprint;
    private String sts;//Status
    private Date adat = null;//Achieved Date
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getBlueprint() {
		return blueprint;
	}
	public void setBlueprint(String blueprint) {
		this.blueprint = blueprint;
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
