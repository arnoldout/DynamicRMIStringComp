package ie.gmit.sw;

public class THE_JOB_CONTAINER {
	private Long jobId;
	private String strA;
	private String strB;

	public THE_JOB_CONTAINER(Long jobId, String str1, String str2) {
		super();
		this.jobId = jobId;
		this.strA = str1;
		this.strB = str2;
	}
	
	public String getStr1() {
		return strA;
	}
	public void setStr1(String str1) {
		this.strA = str1;
	}
	public String getStr2() {
		return strB;
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
