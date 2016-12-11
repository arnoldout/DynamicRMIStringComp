package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;
/**
 * 
 * @author Oliver Arnold
 * Has one method that compares two strings when given an algorithm
 * Returns a Resultator
 *
 */
public interface StringService extends Remote {
	Resultator compare(String str1, String str2, ComparerType comp) throws RemoteException;

}