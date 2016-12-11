package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

/**
 * 
 * @author Oliver Arnold
 * Remote object that compares two strings
 *
 */
public class StringServiceImpl extends UnicastRemoteObject implements StringService{
	private static final long serialVersionUID = -11;
	
	public StringServiceImpl() throws RemoteException {
		super();	
	}

	/**
	 * Returns an empty resultator that client and server can both handle
	 * @param str1 - First String
	 * @param str2 - Second String
	 * @param comp - alogorithm type
	 */
	public Resultator compare(String str1, String str2, ComparerType comp) throws RemoteException
	{
		//use singleton to get instance of comparer
		StringComparable sc = AlgoService.getInstance().getComparable(comp);
		//create new resultator
		Resultator res = new ResultatorImpl();
		//using anon thread as client has already got a thread pool to control access
		new Thread() {
			public void run() {
				try {
					//process result, store in resultator
					res.setResult(""+sc.distance(str1, str2));
					res.setProcessed();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		return res;
	}
}