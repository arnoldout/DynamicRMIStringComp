package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class RmiFacade {
	
	public static void main(String[] args) throws Exception{
		StringService ss = new StringServiceImpl();
		LocateRegistry.createRegistry(1099);
		
		//StringCompareMaker scm = new StringCompareMaker();
		Naming.rebind("StringService", ss);
		System.out.println("Server ready.");
	}
}