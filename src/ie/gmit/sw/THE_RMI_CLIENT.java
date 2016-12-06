package ie.gmit.sw;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class THE_RMI_CLIENT {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {		
		THE_FACADE ss = (THE_FACADE) Naming.lookup("rmi://localhost:1099/StringService");
		ss.setAlgo(StringComparer.DamerauLevenshtein);
		int i = ss.compare("asd", "asd");
		System.out.println(i);
		//String encodedResult = ms.getMessage(enc);
		//Print out the message from the message object.
		//System.out.println(encodedResult);
	}
	
}
