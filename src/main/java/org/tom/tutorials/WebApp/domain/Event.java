package org.tom.tutorials.WebApp.domain;

import java.util.Date;

public class Event {
	private Long id;
	private String title;
	private Date date;

	public Event() {
		date = new Date();
	}

	public Event(Event event) {
		id = new Long(event.id);
		title = new String("" + event.title);
		date = (Date) event.date.clone();
	}

	public Long getId() {
		return id;
	}

	private void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}