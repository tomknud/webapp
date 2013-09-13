package org.tom.tutorials.WebApp.domain;

import java.util.Date;
import java.util.Set;
import java.util.HashSet;

public class Person {

	private Long id;
	private int age;
	private String first;
	private String middle;
	private String last;
	private Date date;
	private Set<Event> events = new HashSet<Event>();
	private Set<Address> addresses = new HashSet<Address>();
	private Set<String> emails = new HashSet<String>();
	private Set<Phone> phones = new HashSet<Phone>();
	public Person() {
		date = new Date();
	}

	public Person(Person person) {
		id = new Long(person.id);
		first = new String(""+person.first);
		last = new String(""+person.last);
		age = person.age;
		date = (Date) person.date.clone();
		events.addAll(person.events);
		addresses.addAll(person.addresses);
		emails.addAll(person.emails);
		phones.addAll(phones);
	}

	public Set<Address> getAddresses() {
		return addresses;
	}

	public int getAge() {
		return age;
	}

	public Date getDate() {
		return date;
	}

	public Set<String> getEmails() {
		return emails;
	}

	public Set<Event> getEvents() {
		return events;
	}

	public String getFirst() {
		return first;
	}

	public Long getId() {
		return id;
	}

	public String getLast() {
		return last;
	}

	public String getMiddle() {
		return middle;
	}

	public Set<Phone> getPhones() {
		return phones;
	}

	public void setAddresses(Set<Address> addresses) {
		this.addresses = addresses;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setEmails(Set<String> emails) {
		this.emails = emails;
	}

	public void setEvents(Set<Event> events) {
		this.events = events;
	}

	public void setFirst(String first) {
		this.first = first;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLast(String last) {
		this.last = last;
	}

	public void setMiddle(String Middle) {
		this.middle = Middle;
		
	}

	public void setPhones(Set<Phone> phones) {
		this.phones = phones;
	}
}