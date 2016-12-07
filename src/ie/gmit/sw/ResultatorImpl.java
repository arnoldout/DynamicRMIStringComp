package ie.gmit.sw;

import java.rmi.RemoteException;

public class ResultatorImpl implements Resultator {

	private boolean processed;
	private String result;

	public ResultatorImpl() {
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
