package ie.gmit.sw;

public class Requester{
	private Long jobId;
	private String strA;
	private String strB;
	private StringComparer sc;
	
	public Requester(Long jobId, String str1, String str2, StringComparer sc)  {
		super();
		this.jobId = jobId;
		this.strA = str1;
		this.strB = str2;
		this.sc = sc;
	}
	public StringComparer getComparer() 
	{
		return sc;
	}
	public void setComparer(StringComparer sc) {
		this.sc = sc;
	}
	
	public String getStr1() {
		//encapsulating
		return new String(strA);
	}
	public void setStr1(String str1) {
		this.strA = str1;
	}
	public String getStr2() {
		//encapsulating
		return new String(strB);
	}
	public void setStr2(String str2) {
		this.strB = str2;
	}
	public Long getJobId() {
		return jobId;
	}
	public void setJobId(Long jobId) {
		this.jobId = jobId;
	}	
}
