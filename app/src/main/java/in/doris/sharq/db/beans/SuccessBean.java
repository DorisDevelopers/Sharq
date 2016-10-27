package in.doris.sharq.db.beans;

public class SuccessBean {
	
	private Long id;
	private Long year;
	private Long week;
	private Long nop;//No of presentations.
	private Long sn;//No. of signup.
	private Long sbv;//No. BV signup.
	private Long ln;//Signup on left.
	private Long lbv;//BV on left.
	private Long rn;//Signup on right.
	private Long rbv;//BV on right.
	private Long cycle;
	private Long step;
	private Long income;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getYear() {
		return year;
	}
	public void setYear(Long year) {
		this.year = year;
	}
	public Long getWeek() {
		return week;
	}
	public void setWeek(Long week) {
		this.week = week;
	}
	public Long getNop() {
		return nop;
	}
	public void setNop(Long nop) {
		this.nop = nop;
	}
	public Long getSn() {
		return sn;
	}
	public void setSn(Long sn) {
		this.sn = sn;
	}
	public Long getSbv() {
		return sbv;
	}
	public void setSbv(Long sbv) {
		this.sbv = sbv;
	}
	public Long getLn() {
		return ln;
	}
	public void setLn(Long ln) {
		this.ln = ln;
	}
	public Long getLbv() {
		return lbv;
	}
	public void setLbv(Long lbv) {
		this.lbv = lbv;
	}
	public Long getRn() {
		return rn;
	}
	public void setRn(Long rn) {
		this.rn = rn;
	}
	public Long getRbv() {
		return rbv;
	}
	public void setRbv(Long rbv) {
		this.rbv = rbv;
	}
	public Long getCycle() {
		return cycle;
	}
	public void setCycle(Long cycle) {
		this.cycle = cycle;
	}
	public Long getStep() {
		return step;
	}
	public void setStep(Long step) {
		this.step = step;
	}
	public Long getIncome() {
		return income;
	}
	public void setIncome(Long income) {
		this.income = income;
	}

 
	// Will be used by the ArrayAdapter in the ListView
    @Override
    public String toString() {
        //return the text required for ArrayAdapter after checking the format.    
    	//return ArrayAdapter readable string;
    	return null;
    }
    	

}
