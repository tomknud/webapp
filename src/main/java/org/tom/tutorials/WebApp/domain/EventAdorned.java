package org.tom.tutorials.WebApp.domain;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.tom.tutorials.WebApp.util.HibernateUtil;

public class EventAdorned extends Event {
	public EventAdorned(Event event) {
		super(event);
	}
	
	public EventAdorned() {
		super();
	}
	
	public static EventAdorned get(Event event)
	{
		if(event != null)
		{
			return new EventAdorned(event);
		}
		else
		{
			return new EventAdorned();
		}
	}
	
	public static Event createAndStoreEvent(String title, Date theDate) {
		Event theEvent = new Event();
		theEvent.setTitle(title);
		theEvent.setDate(theDate);
		HibernateUtil.getSessionFactory().getCurrentSession().save(theEvent);
		return theEvent;
	}
	
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

}
