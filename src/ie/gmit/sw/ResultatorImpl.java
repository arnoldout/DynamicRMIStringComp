package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
/**
 * @author Oliver Arnold
 *	Implementation of Resultator interface
 */
public class ResultatorImpl extends UnicastRemoteObject implements Resultator {

	private static final long serialVersionUID = 6644542;
	
	private boolean processed;
	private String result;
	/**
	 * New Resultator has a boolean that should change when result is added 
	 * @throws RemoteException
	 */
	public ResultatorImpl() throws RemoteException{
		super();
		processed = false;
		result = null;
	}
	/**
	 * Should not be called before checking state of processed
	 */
	public String getResult() throws RemoteException {
		return result;
	}
	public void setResult(String result) throws RemoteException {		
		this.result = result;
	}
	public boolean isProcessed() throws RemoteException {
		return this.processed;
	}
	/**
	 * Processed set, should now set result
	 */
	public void setProcessed() throws RemoteException {
		this.processed = true;
	}
}
