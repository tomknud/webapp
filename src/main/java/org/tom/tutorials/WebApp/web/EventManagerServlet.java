package org.tom.tutorials.WebApp.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.tom.tutorials.WebApp.domain.AddressAdorned;
import org.tom.tutorials.WebApp.domain.Event;
import org.tom.tutorials.WebApp.domain.EventAdorned;
import org.tom.tutorials.WebApp.domain.PersonAdorned;
import org.tom.tutorials.WebApp.util.HibernateUtil;

public class EventManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	private void printEventForm(PrintWriter out) {
		out.println("<h2>Add new event:</h2>");
		out.println("<form>");
		out.println("Title: <input name='eventTitle' length='50'/><br/>");
		out.println("Date (e.g. 24.12.2009): <input name='eventDate' length='10'/><br/>");
		out.println("<input type='submit' name='action' value='storeEvent'/>");
		out.println("</form>");
	}

	private void listEvents(PrintWriter out, SimpleDateFormat dateFormatter) {
		List result = HibernateUtil.getSessionFactory().getCurrentSession()
				.createCriteria(Event.class).list();
		if (result.size() > 0) {
			out.println("<h2>Events in database:</h2>");
			out.println("<table border='1'>");
			out.println("<tr>");
			out.println("<th>Event title</th>");
			out.println("<th>Event date</th>");
			out.println("</tr>");
			Iterator it = result.iterator();
			while (it.hasNext()) {
				Event event = (Event) it.next();
				out.println("<tr>");
				out.println("<td>" + event.getTitle() + "</td>");
				out.println("<td>" + dateFormatter.format(event.getDate())
						+ "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd.MM.yyyy");
		try {
			// Begin unit of work
			Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			session.beginTransaction();
			// Write HTML header
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>Event Manager</title></head><body>");
			// Handle actions
			if ("storeEvent".equals(request.getParameter("action"))) {
				String eventTitle = request.getParameter("eventTitle");
				String eventDate = request.getParameter("eventDate");
				if ("".equals(eventTitle) || "".equals(eventDate)) {
					out.println("<b><i>Please enter event title and date.</i></b>");
				} else {
					Event event = EventAdorned.createAndStoreEvent(eventTitle,
							dateFormatter.parse(eventDate));
					out.println("<b><i>Added event.</i></b>");
				}
			} else if ("storeAddress".equals(request.getParameter("action"))) {
				try {
					AddressAdorned address = AddressAdorned
							.createAndStoreAddress(request);
					out.println("<b><i>Added address.</i></b>");
					address.publishRow(out);
				} catch (AddressAdorned.IncompleteAddressException e) {
					out.println("<b><i>Please fill in address details.</i></b><b><i>"
							+ e.getLocalizedMessage() + "</i></b>");
				}
			} else if ("storePerson".equals(request.getParameter("action"))) {
			try {
				PersonAdorned address = PersonAdorned.createAndStorePerson(request);
				out.println("<b><i>Added person.</i></b>");
				address.publishRow(out);
			} catch (PersonAdorned.IncompletePersonException e) {
				out.println("<b><i>Please fill in sufficient person details.</i></b><b><i>"
						+ e.getLocalizedMessage() + "</i></b>");
			}
		}
			// Print page
			printEventForm(out);
			listEvents(out, dateFormatter);
			AddressAdorned.publishForm(out);
			AddressAdorned.listTable(session, out);
			PersonAdorned.publishForm(out);
			PersonAdorned.listTable(session, out);
			// Write HTML footer
			out.println("</body></html>");
			out.flush();
			out.close();
			// End unit of work
			session.getTransaction().commit();
		} catch (Exception ex) {
			printError(response.getWriter(), ex);
			if (ServletException.class.isInstance(ex)) {
				HibernateUtil.getSessionFactory().getCurrentSession()
						.getTransaction().rollback();
				throw (ServletException) ex;
			} else {
			}
		}
	}

	private void printError(PrintWriter out, Exception ex) {
		out.println("<html><head><title>Event Manager</title></head><body>");
		out.println("An Exception has occured: " + ex.getMessage());
		// Write HTML footer
		out.println("</body></html>");
		out.flush();
		out.close();
	}
}