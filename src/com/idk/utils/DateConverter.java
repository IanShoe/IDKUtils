/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.sql.Timestamp;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author shoemaki
 */
public class DateConverter {
    

    /**
     * Method to convert universal date object to a XMLGregorianCalendar with the UTC zone.
     * @param date universal date object
     * @return newly created XMLGregorianCalendar
     * @throws DatatypeConfigurationException
     */
    public static XMLGregorianCalendar toXMLGregorianCalendar(Object date) throws DatatypeConfigurationException {
	return DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) toDateTime(date).toGregorianCalendar());
    }

    /**
     * Method to convert universal date object to a timeStamp with the UTC zone.
     * @param date universal date object
     * @return newly created Timestamp
     */
    public static Timestamp toTimeStamp(Object date) {
	return new Timestamp(toDateTime(date).getMillis());
    }

    /**
     * Method to convert universal date object to a java.sql.Date with the UTC zone.
     * @param date universal date object
     * @return newly created date
     */
    public static java.sql.Date toSqlDate(Object date) {
	return new java.sql.Date(toDateTime(date).toDate().getTime());
    }

    /**
     * Wrapper method to take a universal date object and give the string representation using
     * the control station's default format.
     * @param date universal date object
     * @return String representation of date
     */
    public static String toString(Object date) {
	return toString(date, "MM-dd-yyyy HH:mm:ss");
    }

    /**
     * Method to take a universal date object and give the string representation using
     * the given format.
     * @param date universal date object
     * @return String representation of date.
     */
    public static String toString(Object date, String pattern) {
	DateTime dateTime = toDateTime(date);
	DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
	if (dateTime != null) {
	    return dateTime.toString(dtf);
	}
	else {
	    return null;
	}
    }

    /**
     * Method to take a universal date object and convert it into a joda DateTime object.
     * This method will ensure the time is captured with the UTC zone.
     * @param date universal date object
     * @return newly created dateTime object
     */

    public static DateTime toDateTime(Object date) {
	Class<? extends Object> clazz = date.getClass();
	DateTime dateTime = null;
	if (clazz == DateTime.class) {
	    dateTime = (DateTime) date;
	}
	else if (clazz == XMLGregorianCalendarImpl.class || clazz == XMLGregorianCalendar.class) {
	    XMLGregorianCalendar xmlDate = (XMLGregorianCalendar) date;
	    dateTime = new DateTime(xmlDate.toGregorianCalendar().getTime());
	}
	else if (clazz == GregorianCalendar.class) {
	    GregorianCalendar gc = (GregorianCalendar) date;
	    dateTime = new DateTime(gc.getTime());
	}
	else if (clazz == Date.class || clazz == java.sql.Date.class) {
	    dateTime = new DateTime((Date) date);
	}
	else if (clazz == Timestamp.class) {
	    Timestamp timeStamp = (Timestamp) date;
	    dateTime = new DateTime(timeStamp.getTime());
	}
	else {
	    //Maybe throw a new exception
	    return null;
	}
	return dateTime.withZone(DateTimeZone.UTC);
    }
}
