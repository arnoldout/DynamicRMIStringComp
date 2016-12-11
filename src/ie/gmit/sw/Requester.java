package ie.gmit.sw;

/**
 * Request object that stores all required data on the job to be processes
 * @author Oliver Arnold
 */
public class Requester{
	private Long jobId;
	private String strA;
	private String strB;
	private ComparerType sc;

	/**
	 * 
	 * @param jobId - Number id of this request
	 * @param str1 - First String to be compared
	 * @param str2 - Second String to be compared
	 * @param sc - Instance of StringComparer
	 */
	public Requester(Long jobId, String str1, String str2, ComparerType sc)  {
		super();
		this.jobId = jobId;
		this.strA = str1;
		this.strB = str2;
		this.sc = sc;
	}
	public ComparerType getComparer() 
	{
		return sc;
	}
	public void setComparer(ComparerType sc) {
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
