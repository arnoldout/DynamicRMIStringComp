package ie.gmit.sw;

import java.rmi.RemoteException;

public class StringCompareMaker {
	private StringComparable sc;

	public StringCompareMaker(StringComparer eComparer) throws RemoteException {
		super();
		
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
	public int distance(String s, String t)
	{
		return sc.distance(s, t);
	}
}
