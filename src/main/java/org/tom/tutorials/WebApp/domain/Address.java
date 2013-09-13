package org.tom.tutorials.WebApp.domain;

import java.util.Date;

public class Address {
  private Long   id;
  private String lineOne;
  private String lineTwo;
  private String town;
  private String state;
  private String country;
  private String zip;
  private Date   date;
  
  public Address()
  {
	lineOne = "Unknown";
	lineTwo = "Unknown";
	town = "Unknown";
	state = "Unknown";
	country = "Unknown";
	zip = "Unknown";
    date = new Date();
  }

  public Address(Address address) {
	id = new Long(address.id);
	lineOne = new String(""+address.lineOne);
	lineTwo = new String(""+address.lineTwo);
	town = new String(""+address.town);
	state = new String(""+address.state);
	country = new String(""+address.country);
	zip = new String(""+address.zip);
	date = (Date) address.date.clone();
  }

  public String getCountry() {
    return country;
  }

  public Date getDate() {
    return date;
  }

  public Long getId() {
    return id;
  }

  public String getLineOne() {
    return lineOne;
  }

  public String getLineTwo() {
    return lineTwo;
  }

  public String getState() {
    return state;
  }

  public String getTown() {
    return town;
  }

  public String getZip() {
    return zip;
  }

  public void setCountry(String country) {
	  this.country = country;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setLineOne(String lineOne) {
    this.lineOne = lineOne;
  }

  public void setLineTwo(String lineTwo) {
    this.lineTwo = lineTwo;
  }

  public void setState(String state) {
	  this.state = state;
  }
  
  public void setTown(String town) {
    this.town = town;
  }

  public void setZip(String zip) {
    this.zip = zip;
  }
    
}