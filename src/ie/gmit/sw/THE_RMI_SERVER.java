package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class THE_RMI_SERVER {
	public static void main(String[] args) throws Exception{
		THE_FACADE ss = new THE_FACADE();
		LocateRegistry.createRegistry(1099);
		
		//facade
		//StringCompareMaker scm = new StringCompareMaker();
		Naming.rebind("StringService", ss);
		System.out.println("Server ready.");
	}
}
