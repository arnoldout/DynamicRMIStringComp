package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ResultatorImpl extends UnicastRemoteObject implements Resultator {

	private static final long serialVersionUID = 6644542;
	private boolean processed;
	private String result;

	public ResultatorImpl() throws RemoteException{
		super();
		processed = false;
		result = null;
	}
	
	public String getResult() throws RemoteException {
		return result;
	}
	public void setResult(String result) throws RemoteException {		
		this.result = result;
	}
	public boolean isProcessed() throws RemoteException {
		return this.processed;
	}
	public void setProcessed() throws RemoteException {
		this.processed = true;
	}
}
