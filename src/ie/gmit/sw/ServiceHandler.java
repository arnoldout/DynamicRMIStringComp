package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceHandler extends HttpServlet {
	private String remoteHost = null;
	private static AtomicLong jobNumber;

	private BlockingQueue<Requester> inQueue = new ArrayBlockingQueue<Requester>(100);
	private Map<Long, Resultator> outQueue = new HashMap<Long, Resultator>();

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); // Reads the value from
		jobNumber = new AtomicLong(0);						// the
															// <context-param>
															// in web.xml
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();
		
		// Initialise some request varuables with the submitted form info. These
		// are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		out.print("<html><head><title>Distributed Systems Assignment</title>");
		out.print("</head>");
		out.print("<body>");

		if (taskNumber == null) {
			taskNumber = new String("T" + jobNumber);
			jobNumber.getAndIncrement();
			Requester r = new Requester(jobNumber.get(), t, s, AlgoService.getComparer(algorithm));
			try {
				inQueue.put(r);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Add job to in-queue
		} else {
			// Check out-queue for finished job
			if(outQueue.containsKey(jobNumber)){
				if(outQueue.get(jobNumber).isProcessed())
				{
					out.print(outQueue.get(jobNumber).getResult());
				}

			}
		}

		out.print("<H1>Processing request for Job#: " + taskNumber + "</H1>");
		out.print("<div id=\"r\"></div>");

		out.print("<font color=\"#993333\"><b>");
		out.print("RMI Server is located at " + remoteHost);
		out.print("<br>Algorithm: " + algorithm);
		out.print("<br>String <i>s</i> : " + s);
		out.print("<br>String <i>t</i> : " + t);
		out.print(
				"<br>This servlet should only be responsible for handling client request and returning responses. Everything else should be handled by different objects.");
		out.print(
				"Note that any variables declared inside this doGet() method are thread safe. Anything defined at a class level is shared between HTTP requests.");
		out.print("</b></font>");

		out.print("<P> Next Steps:");
		out.print("<OL>");
		out.print(
				"<LI>Generate a big random number to use a a job number, or just increment a static long variable declared at a class level, e.g. jobNumber.");
		out.print("<LI>Create some type of an object from the request variables and jobNumber.");
		out.print("<LI>Add the message request object to a LinkedList or BlockingQueue (the IN-queue)");
		out.print(
				"<LI>Return the jobNumber to the client web browser with a wait interval using <meta http-equiv=\"refresh\" content=\"10\">. The content=\"10\" will wait for 10s.");
		out.print("<LI>Have some process check the LinkedList or BlockingQueue for message requests.");
		out.print(
				"<LI>Poll a message request from the front of the queue and make an RMI call to the String Comparison Service.");
		out.print(
				"<LI>Get the <i>Resultator</i> (a stub that is returned IMMEDIATELY by the remote method) and add it to a Map (the OUT-queue) using the jobNumber as the key and the <i>Resultator</i> as a value.");
		out.print(
				"<LI>Return the result of the string comparison to the client next time a request for the jobNumber is received and the <i>Resultator</i> returns true for the method <i>isComplete().</i>");
		out.print("</OL>");

		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
		out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		out.print("<script>");
		out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
		out.print("</script>");

		// You can use this method to implement the functionality of an RMI
		// client
		new Thread() {
			public void run() {
				try {
					if(!inQueue.isEmpty())
					{
						Requester req = inQueue.poll();
						StringService ss = (StringService) Naming.lookup("rmi://localhost:1099/StringService");
						Resultator res = ss.compare(req);
						outQueue.put(req.getJobId(), res);
						Thread.sleep(10000);
					}
				} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.start();

		//
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}