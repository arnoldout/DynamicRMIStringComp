package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringService extends Remote {
	Resultator compare(String str1, String str2, StringComparer comp) throws RemoteException;

}