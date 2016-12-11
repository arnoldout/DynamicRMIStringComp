package ie.gmit.sw;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

public class Servant {
	/**
	 * RMI Runner that is set on port 1099 under the name StringService
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		StringService ss = new StringServiceImpl();
		LocateRegistry.createRegistry(1099);
		
		//StringCompareMaker scm = new StringCompareMaker();
		Naming.rebind("StringService", ss);
		System.out.println("Server ready.");
	}
}