package ie.gmit.sw;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class StringServiceImpl extends UnicastRemoteObject implements StringService{
	private static final long serialVersionUID = -11;
	
	public StringServiceImpl() throws RemoteException {
		super();	
	}

	public Resultator compare(String str1, String str2, StringComparer comp) throws RemoteException
	{
		System.out.println(comp);
		StringComparable sc = AlgoService.getComparable(comp);
		Resultator res = new ResultatorImpl();
		new Thread() {
			public void run() {
				try {
					Thread.sleep(1000);
					res.setResult(""+sc.distance(str1, str2));
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
}
