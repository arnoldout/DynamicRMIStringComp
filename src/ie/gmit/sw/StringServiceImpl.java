package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService{
	private StringComparable sc;
	private Requester r;
	public StringServiceImpl() throws RemoteException {
		super();	
	}
	
	private void setAlgo(StringComparer eComparer){
		switch (eComparer) {
		case DamerauLevenshtein:
			sc = new DamerauLevenshtein();
			break;
		case HammingDistance:
			sc = new HammingDistance();
			break;
		case Levenshtein:
			sc = new Levenshtein();
			break;
		}
	}
	public Resultator compare(Requester r) throws RemoteException
	{
		setAlgo(r.getSc());
		Resultator res = new ResultatorImpl();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(10000);
					res.setResult(""+sc.distance(r.getStr1(), r.getStr2()));
					res.setProcessed();
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();
		return res;
	}
	public Requester getR() {
		return r;
	}
	public void setR(Requester r) {
		this.r = r;
	}
}
