package org.tom.tutorials.WebApp;

import org.hibernate.Session;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.tom.tutorials.WebApp.domain.Event;
import org.tom.tutorials.WebApp.domain.Person;
import org.tom.tutorials.WebApp.domain.PersonAdorned;
import org.tom.tutorials.WebApp.util.HibernateUtil;

public class EventManager {

	public static void main(String[] args) {
		final EventManager mgr = new EventManager();
		JFrame frame = new JFrame();
		JPanel personInput = new JPanel();
		JPanel total = new JPanel();
		final JTextArea tFirst = new JTextArea(1,12);
		final JTextArea tMiddle = new JTextArea(1,2);
		final JTextArea tLast = new JTextArea(1,12);
		final JTextArea tAge = new JTextArea(1,12);
		final JTextArea tResult = new JTextArea(3,36);
		JButton bSubmit = new JButton();
		bSubmit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				PersonAdorned.createAndStorePerson(tFirst.getText(), tMiddle.getText(),tLast.getText(),tAge.getText());
				tResult.setText(tFirst.getText() +" "+ tMiddle.getText()+" age "+tAge.getText());
			}
		});
		personInput.add(tFirst);
		personInput.add(tMiddle);
		personInput.add(tLast);
		personInput.add(tAge);
		personInput.add(bSubmit);
		total.add(personInput);
		total.add(tResult);
		frame.add(total);
		frame.pack();
		frame.setVisible(true);
	}

	private void things(String[] args)
	{
		for (int io = 0; io < 100; io++) {
			if (args[0].equals("store")) {
				createAndStoreEvent("My Event", new Date());
			} else if (args[0].equals("list")) {
				List events = listEvents();
				for (int i = 0; i < events.size(); i++) {
					Event theEvent = (Event) events.get(i);
					System.out.println("Event: " + theEvent.getTitle()
							+ " Time: " + theEvent.getDate());
				}
			} else if (args[0].equals("addpersontoevent")) {
				Long eventId = createAndStoreEvent("My Event", new Date());
				Long personId = createAndStorePerson("Foo", "Bar");
				addPersonToEvent(personId, eventId);
				System.out.println("Added person " + personId + " to event "
						+ eventId);
			}
			sleep(10000);
		}
		sleep(1000000);
		HibernateUtil.getSessionFactory().close();
	}
	public static void sleep(int sTime) {
		try {
			Thread.sleep(sTime);
		} catch (InterruptedException e) {
		}
	}

	private List listEvents() {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		List result = session.createQuery("from Event").list();
		session.getTransaction().commit();
		return result;
	}

	private void addPersonToEvent(Long personId, Long eventId) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person aPerson = (Person) session.load(Person.class, personId);
		Event anEvent = (Event) session.load(Event.class, eventId);
		aPerson.getEvents().add(anEvent);

		session.getTransaction().commit();
	}

	private void addEmailToPerson(Long personId, String email) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person aPerson = (Person) session.load(Person.class, personId);
		// adding to the emailAddress collection might trigger a lazy load of
		// the collection
		aPerson.getEmails().add(email);

		session.getTransaction().commit();
	}

	private long createAndStoreEvent(String title, Date theDate) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Event theEvent = new Event();
		theEvent.setTitle(title);
		theEvent.setDate(theDate);
		session.save(theEvent);

		session.getTransaction().commit();
		return theEvent.getId();
	}

	private long createAndStorePerson(String first, String last) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();

		Person thePerson = new Person();
		thePerson.setFirst(first);
		thePerson.setLast(last);
		session.save(thePerson);

		session.getTransaction().commit();
		return thePerson.getId();
	}

}