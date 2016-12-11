package ie.gmit.sw;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Map;

/**
 * 
 * @author Oliver Arnold
 * Runner class that processes the next job
 *
 */
public class RequestRunner implements Runnable{
	private Map<String, Resultator> outQueue;
	private Requester req;
	public RequestRunner(Map<String, Resultator> outQueue, Requester req) {
		this.outQueue = outQueue;
		this.req = req;
	}

	/**
	 * Run this job
	 */
	public void run() {
		try {
			System.out.println("Started Requesting "+req.getJobId());
			StringService ss = (StringService) Naming.lookup("rmi://localhost:1099/StringService");
			Resultator res = ss.compare(req.getStr1(), req.getStr2(), req.getComparer());
			outQueue.put(req.getJobId().toString(), res);
			Thread.sleep(1000);
		} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}