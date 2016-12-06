package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

public class THE_FACADE implements Remote{
	private StringComparable sc;
	
	public THE_FACADE() throws RemoteException {
		super();
	}
	public void setAlgo(StringComparer eComparer) {
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
	public int compare(String s, String t) throws RemoteException
	{
		return sc.distance(s, t);
	}
}
