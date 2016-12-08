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
	private static final long serialVersionUID = 229626802L;
	private String remoteHost = null;
	private static AtomicLong jobNumber;

	private BlockingQueue<Requester> inQueue = new ArrayBlockingQueue<Requester>(100);
	private Map<String, Resultator> outQueue = new HashMap<String, Resultator>();

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); // Reads the value from
		jobNumber = new AtomicLong(0); // the
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
		boolean isFinished = false;
		if (taskNumber == null) {
			taskNumber = new String(jobNumber.toString());
			Requester r = new Requester(jobNumber.getAndIncrement(), t, s, AlgoService.getComparer(algorithm));
			try {
				inQueue.put(r);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// Add job to in-queue
		} else {
			// Check out-queue for finished job
			if (outQueue.containsKey(taskNumber)) {
				if (outQueue.get(taskNumber).isProcessed()) {
					isFinished = true;
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
		out.print("<form name=\"frmRequestDetails\">");
		out.print("<input name=\"cmbAlgorithm\" type=\"hidden\" value=\"" + algorithm + "\">");
		out.print("<input name=\"txtS\" type=\"hidden\" value=\"" + s + "\">");
		out.print("<input name=\"txtT\" type=\"hidden\" value=\"" + t + "\">");
		out.print("<input name=\"frmTaskNumber\" type=\"hidden\" value=\"" + taskNumber + "\">");
		out.print("</form>");
		out.print("</body>");
		out.print("</html>");

		if(isFinished)
		{
			out.print("FINISHED\n");
			out.print("Result :"+outQueue.get(taskNumber).getResult());
		}
		else{
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
			out.print("</script>");
		}

		// You can use this method to implement the functionality of an RMI
		// client
		if (!inQueue.isEmpty()) {
			new Thread() {
				public void run() {
					try {
 						Requester req = inQueue.poll();
						System.out.println("Started Requesting "+req.getJobId());
						StringService ss = (StringService) Naming.lookup("rmi://localhost:1099/StringService");
						Resultator res = ss.compare(req.getStr1(), req.getStr2(), req.getComparer());
						outQueue.put(req.getJobId().toString(), res);
						Thread.sleep(1000);
					} catch (MalformedURLException | RemoteException | NotBoundException | InterruptedException e) {
						e.printStackTrace();
					}
				}
			}.start();
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}