package in.doris.sharq.db.beans;

import java.sql.Date;

public class MstUserBean {

	private Long id;
    private String ir;//IR Id.
    private String name;
    private Long phone;
    private String email;
    private Date datjnd;//Date Joined.
    private String lname;//Leader's Name.
    private String rank;
    private String checklist;
    private String ccy;//Currency.
    
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getIr() {
		return ir;
	}
	public void setIr(String ir) {
		this.ir = ir;
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Date getDatjnd() {
		return datjnd;
	}
	public void setDatjnd(Date datjnd) {
		try{
			this.datjnd = datjnd;
		}catch(Exception e){
			e.printStackTrace();
		}

	}
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getChecklist() {
		return checklist;
	}
	public void setChecklist(String checklist) {
		this.checklist = checklist;
	}
	public String getCcy() {
		return ccy;
	}
	public void setCcy(String ccy) {
		this.ccy = ccy;
	}
    
	// Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        //return the text required for ArrayAdapter after checking the format.    
    	//return ArrayAdapter readable string;
    	return null;
    }
    
}
