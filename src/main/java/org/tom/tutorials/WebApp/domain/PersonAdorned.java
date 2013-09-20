package org.tom.tutorials.WebApp.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.hibernate.Session;
import org.tom.tutorials.WebApp.util.HibernateUtil;

public class PersonAdorned extends Person {
	public PersonAdorned(Person person) {
		super(person);
	}
	
	public PersonAdorned() {
		super();
	}
	
	public static PersonAdorned get(Person person)
	{
		if(person != null)
		{
			return new PersonAdorned(person);
		}
		else
		{
			return new PersonAdorned();
		}
	}
	
	private void addEvent(Session session, Long eventId) {
		session.beginTransaction();
		
		Event anEvent = (Event) session.load(Event.class, eventId);
		getEvents().add(anEvent);
		
		session.getTransaction().commit();
	}
	
	public static void listTable(Session session, PrintWriter out){
		List<Person> result = session.createCriteria(Person.class).list();
		if (result.size() > 0) {
			out.println("<h2>Personess in database:</h2>");
			out.println("<table border='1'>");
			PersonAdorned.publishHeader(out);
			for (Person Person : result) {
				PersonAdorned.get(Person).publishRow(out);
			}
			out.println("</table>");
		}
	}
	
  public static JPanel createForm(JTextArea tResultIn)
  {
    JPanel addressInput = new JPanel();
    final JTextArea tFirst = new JTextArea(1,20);
    final JTextArea tMiddle = new JTextArea(1,20);
    final JTextArea tLast = new JTextArea(1,20);
    final JTextArea tAge = new JTextArea(1,3);
    final JTextArea tResult = tResultIn;
    JButton bSubmit = new JButton();
    bSubmit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        createAndStorePerson(tFirst.getText(), tMiddle.getText(),tLast.getText(),tAge.getText());
        tResult.setText("First: "+tFirst.getText() +" M: "+ tMiddle.getText()+" Last: "+ tLast.getText()+" age: "+tAge.getText());
      }
    });
    addressInput.add(tFirst);
    addressInput.add(tMiddle);
    addressInput.add(tLast);
    addressInput.add(tAge);
    addressInput.add(bSubmit);
    PrintWriter pw = null;
    StringWriter sw = new StringWriter();
    pw = new PrintWriter(sw);
	Session session = HibernateUtil.getSessionFactory().getCurrentSession();
	session.beginTransaction();
    listTable(session,pw);
    tResult.setText(sw.getBuffer().toString());
    session.getTransaction().commit();
    return addressInput;
  }

	public static void publishForm(PrintWriter out) {
		out.println("<h2>Add new person:</h2>");
		out.println("<form>");
		out.println("Fist Name: <input name='personFirst' length='50'/><br/>");
		out.println("Last Name: <input name='personLast' length='50'/><br/>");
		out.println("Age: <input name='personAge' length='50'/><br/>");
		out.println("<input type='submit' name='action' value='storePerson'/>");
		out.println("</form>");
	}

	public void publishRow(PrintWriter out) {
		out.println("<tr>");
		out.println("<td>" + getFirst() + "</td>");
		out.println("<td>" + getLast() + "</td>");
		out.println("<td>" + getAge() + "</td>");
		out.println("<td>" + SimpleDateFormat.getInstance().format(getDate()) + "</td>");
		out.println("</tr>");
	}
	
	public static void publishHeader(PrintWriter out)
	{
		out.println("<tr>");
		out.println("<th>Person First Name</th>");
		out.println("<th>Person Last Name</th>");
		out.println("<th>Person Age</th>");
		out.println("<th>Added</th>");
		out.println("</tr>");
	}

	public static PersonAdorned createAndStorePerson(HttpServletRequest request)
			throws IncompletePersonException {
		String personFirst = request.getParameter("personFirst");
		String personLast = request.getParameter("personLast");
		int personAge = Integer.parseInt(request.getParameter("personAge"));
		if ("".equals(personFirst)) {
			throw new IncompletePersonException("No First Name Component");
		} else if ("".equals(personLast)) {
			throw new IncompletePersonException("No Last Name Component");
		} else if (personAge == 0) {
			throw new IncompletePersonException("No Age Component");
		}
		Person thePerson = new Person();
		thePerson.setFirst(personFirst);
		thePerson.setLast(personLast);
		thePerson.setAge(personAge);
		HibernateUtil.getSessionFactory().getCurrentSession().save(thePerson);
		return get(thePerson);
	}

	public static class IncompletePersonException extends Exception {
		public IncompletePersonException(String message) {
			super(message);
		}
	}

	public static void createAndStorePerson(String personFirst, String personMiddle, String personLast, String personAge) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Person thePerson = new Person();
		thePerson.setFirst(personFirst);
		thePerson.setMiddle(personMiddle);
		thePerson.setLast(personLast);
		thePerson.setAge(Integer.parseInt(personAge));
		session.save(thePerson);
		session.getTransaction().commit();
	}

}
