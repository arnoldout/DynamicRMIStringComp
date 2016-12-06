package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class MessageServer {
	public static void main(String[] args) throws Exception{
		//MessageService ss = new MessageServiceImpl();
		LocateRegistry.createRegistry(1099);
		
		//facade
		//StringCompareMaker scm = new StringCompareMaker();
		//Naming.rebind("StringService", ss);
		System.out.println("Server ready.");
	}
}
