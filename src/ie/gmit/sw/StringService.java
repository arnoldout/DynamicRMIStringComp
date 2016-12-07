package ie.gmit.sw;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface StringService extends Remote {
	Resultator compare(Requester r) throws RemoteException;

}