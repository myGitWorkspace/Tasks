package src.java.control;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;

import src.java.control.action.ActionFactory;
import src.java.control.action.ActionCommand;
import src.java.control.action.ConfigurationManager;
import src.java.control.action.MessageManager;


import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.BasicConfigurator;
import java.io.File;


import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Controller
 */
@WebServlet("/Controller")
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();        
    }

    public void init(ServletConfig config) throws ServletException {
		System.out.println("Log4JInitServlet is initializing log4j");
		//String log4jLocation = config.getInitParameter("log4j-properties-location");
		String log4jLocation = "WEB-INF\\classes\\log4j.properties";
		
		ServletContext sc = config.getServletContext();

		if (log4jLocation == null) {
			System.err.println("*** No log4j-properties-location init param, so initializing log4j with BasicConfigurator");
			BasicConfigurator.configure();
		} else {
			String webAppPath = sc.getRealPath("/");
			String log4jProp = webAppPath + log4jLocation;
			System.out.println(log4jProp);
			File file = new File(log4jProp);
			if (file.exists()) {
				System.out.println("Initializing log4j with: " + log4jProp);				
				PropertyConfigurator.configure(log4jProp);
			} else {
				System.err.println("*** " + log4jProp + " file not found, so initializing log4j with BasicConfigurator");
				BasicConfigurator.configure();
			}
		}
		super.init(config);		
		
	}
    
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		processRequest(request, response);		
	}
	
		
	private void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String page = null;
		 		
		ActionFactory client = new ActionFactory();
		ActionCommand command = client.defineCommand(request);
		//src.java.control.ControlLogger log = new src.java.control.ControlLogger();
		//log.makeLog(command);
		page = command.execute(request);
		
		if (page != null) {
			HttpSession session = request.getSession(true);
			if (session.getAttribute("role") == null) {				
				session.setAttribute("role", "moderator");
			}
			Integer counter = (Integer) session.getAttribute("counter");
			if (counter == null) {
				session.setAttribute("counter", 1);
			} else {			
				counter++;
				session.setAttribute("counter", counter);
			}
			
			RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(page);
			dispatcher.forward(request, response);			
		} else {		
			page = ConfigurationManager.getProperty("path.page.index");
			request.getSession().setAttribute("nullPage", MessageManager.getProperty("message.nullpage"));
			response.sendRedirect(request.getContextPath() + page);
		}
		
	}

}
