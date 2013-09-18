package org.tom.tutorials.WebApp.domain;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.hibernate.Session;
import org.tom.tutorials.WebApp.util.HibernateUtil;

public class AddressAdorned extends Address {
	public AddressAdorned(Address address) {
		super(address);
	}
	
	public AddressAdorned() {
		super();
	}
	
	public static AddressAdorned get(Address address)
	{
		if(address != null)
		{
			return new AddressAdorned(address);
		}
		else
		{
			return new AddressAdorned();
		}
	}
	
	public static void listTable(Session session, PrintWriter out){
		List<Address> result = session.createCriteria(Address.class).list();
		if (result.size() > 0) {
			out.println("<h2>Addressess in database:</h2>");
			out.println("<table border='1'>");
			AddressAdorned.publishHeader(out);
			for (Address address : result) {
				AddressAdorned.get(address).publishRow(out);
			}
			out.println("</table>");
		}
	}

	public static void publishForm(PrintWriter out) {
		out.println("<h2>Add new address:</h2>");
		out.println("<form>");
		out.println("Line One: <input name='addressOne' length='50'/><br/>");
		out.println("Line Two: <input name='addressTwo' length='50'/><br/>");
		out.println("City: <input name='addressTown' length='20'/><br/>");
		out.println("State: <input name='addressState' length='10'/><br/>");
		out.println("Zip: <input name='addressZip' length='10'/><br/>");
		out.println("<input type='submit' name='action' value='storeAddress'/>");
		out.println("</form>");
	}
  public static JPanel createForm(JTextArea tResultIn)
  {
    JPanel addressInput = new JPanel();
    final JTextArea tOne = new JTextArea(1,12);
    final JTextArea tTwo = new JTextArea(1,2);
    final JTextArea tTown = new JTextArea(1,12);
    final JTextArea tState = new JTextArea(1,12);
    final JTextArea tZip = new JTextArea(1,12);
    final JTextArea tResult = tResultIn;
    JButton bSubmit = new JButton();
    bSubmit.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent e) {
        createAndStoreAddress(tOne.getText(), tTwo.getText(),tTown.getText(),tState.getText(),tZip.getText());
        tResult.setText(tOne.getText() +" "+ tTwo.getText()+" age "+tState.getText());
      }
    });
    addressInput.add(tOne);
    addressInput.add(tTwo);
    addressInput.add(tTown);
    addressInput.add(tState);
    addressInput.add(bSubmit);
    return addressInput;
  }
	public void publishRow(PrintWriter out) {
		out.println("<tr>");
		out.println("<td>" + getLineOne() + "</td>");
		out.println("<td>" + getLineTwo() + "</td>");
		out.println("<td>" + getTown() + "</td>");
		out.println("<td>" + getState() + "</td>");
		out.println("<td>" + getZip() + "</td>");
		out.println("<td>" + SimpleDateFormat.getInstance().format(getDate()) + "</td>");
		out.println("</tr>");
	}
	
	public static void publishHeader(PrintWriter out)
	{
		out.println("<tr>");
		out.println("<th>Address Line One</th>");
		out.println("<th>Address Line Two</th>");
		out.println("<th>Address City</th>");
		out.println("<th>Address State</th>");
		out.println("<th>Address Zip</th>");
		out.println("<th>Date Added</th>");
		out.println("</tr>");
	}

	public static class IncompleteAddressException extends Exception {
		public IncompleteAddressException(String message) {
			super(message);
		}
	}

	public static AddressAdorned createAndStoreAddress(HttpServletRequest request)
			throws IncompleteAddressException {
		String addressOne = request.getParameter("addressOne");
		String addressTown = request.getParameter("addressTown");
		String addressState = request.getParameter("addressState");
		if ("".equals(addressOne)) {
			throw new IncompleteAddressException("No Address One Component");
		} else if ("".equals(addressTown)) {
			throw new IncompleteAddressException("No City Component");
		} else if ("".equals(addressState)) {
			throw new IncompleteAddressException("No State Component");
		}
		Address theAddress = new Address();
		theAddress.setLineOne(request.getParameter("addressOne"));
		theAddress.setLineTwo(request.getParameter("addressTwo"));
		theAddress.setTown(request.getParameter("addressTown"));
		theAddress.setState(request.getParameter("addressState"));
		theAddress.setZip(request.getParameter("addressZip"));
		HibernateUtil.getSessionFactory().getCurrentSession().save(theAddress);
		return get(theAddress);
	}
	
	public static void createAndStoreAddress(String one, String two, String town, String state, String zip) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		Address theAddress = new Address();
		theAddress.setLineOne(one);
		theAddress.setLineTwo(two);
		theAddress.setTown(town);
		theAddress.setState(state);
		theAddress.setZip(zip);
		session.save(theAddress);
		session.getTransaction().commit();
	}

}
