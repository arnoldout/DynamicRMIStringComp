package ie.gmit.sw;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author Oliver Arnold
 * ServiceHandler processes jobs utilizing an in and out queue
 * using a Threadpool, and incrementing an atomicLong every time a new job is created
 *
 */
public class ServiceHandler extends HttpServlet {
	private static final long serialVersionUID = 229626802L;
	
	private String remoteHost = null;
	private ExecutorService threadPool;
	private static AtomicLong jobNumber;

	private BlockingQueue<Requester> inQueue;
	private Map<String, Resultator> outQueue;

	public void init() throws ServletException {
		ServletContext ctx = getServletContext();
		remoteHost = ctx.getInitParameter("RMI_SERVER"); // Reads the value from the <context-param> in web.xml
		jobNumber = new AtomicLong(0);
		threadPool = Executors.newFixedThreadPool(5);
		inQueue = new ArrayBlockingQueue<Requester>(100);
		outQueue = new ConcurrentHashMap<String, Resultator>();
	}

	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		// Initialize some request variables with the submitted form info. These
		// are local to this method and thread safe...
		String algorithm = req.getParameter("cmbAlgorithm");
		String s = req.getParameter("txtS");
		String t = req.getParameter("txtT");
		String taskNumber = req.getParameter("frmTaskNumber");

		out.print("<html><head><title>Distributed Systems Assignment</title>");
		out.print("</head>");
		out.print("<body>");
		
		//isFinished checks if this job has been processed yet
		boolean isFinished = false;
		if (taskNumber == null) {
			taskNumber = new String(jobNumber.toString());
			try {
				//add to inqueue
				inQueue.put(new Requester(jobNumber.getAndIncrement(), t, s, AlgoService.getInstance().getStringComparerType(algorithm)));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			// Check out-queue for finished job
			if (outQueue.containsKey(taskNumber)) {
				if (outQueue.get(taskNumber).isProcessed()) {
					//job finished
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
			//output result to user
			out.print("FINISHED\n");
			out.print("Result :"+outQueue.get(taskNumber).getResult());
		}
		else{
			//reload after 10 seconds, poll again
			out.print("<script>");
			out.print("var wait=setTimeout(\"document.frmRequestDetails.submit();\", 10000);");
			out.print("</script>");
		}

		//if job exists
		if (!inQueue.isEmpty()) {
			//take job
			Requester request = inQueue.poll();
			//process job
			threadPool.submit(new RequestRunner(outQueue, request));
		}
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doGet(req, resp);
	}
}