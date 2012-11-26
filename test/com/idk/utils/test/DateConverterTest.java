/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.utils.test;

import com.idk.utils.DateConverter;
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
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author shoemaki
 */
public class DateConverterTest {

    @Test
    public void testDateTimeConverters() throws DatatypeConfigurationException {
	DateTime dateTime = new DateTime();
	Date date = dateTime.toDate();
	GregorianCalendar gc = dateTime.toGregorianCalendar();
	XMLGregorianCalendar xmlGC = DatatypeFactory.newInstance().newXMLGregorianCalendar((GregorianCalendar) gc);
	java.sql.Date sqlDate = new java.sql.Date(date.getTime());
	Timestamp timeStamp = new Timestamp(date.getTime());
	Object obj = new Object();
	dateTime = dateTime.withZone(DateTimeZone.UTC);

	assertEquals(dateTime, DateConverter.toDateTime(dateTime));
	assertEquals(dateTime, DateConverter.toDateTime(date));
	assertEquals(dateTime, DateConverter.toDateTime(gc));
	assertEquals(dateTime, DateConverter.toDateTime(xmlGC));
	assertEquals(dateTime, DateConverter.toDateTime(sqlDate));
	assertEquals(dateTime, DateConverter.toDateTime(timeStamp));
	assertNull(DateConverter.toDateTime(obj));

	DateTimeFormatter dtf = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss").withZone(DateTimeZone.UTC);
	String expected = dateTime.toString(dtf);

	assertEquals(expected, DateConverter.toString(dateTime));
	assertEquals(expected, DateConverter.toString(date));
	assertEquals(expected, DateConverter.toString(gc));
	assertEquals(expected, DateConverter.toString(xmlGC));
	assertEquals(expected, DateConverter.toString(sqlDate));
	assertEquals(expected, DateConverter.toString(timeStamp));
	assertNull(DateConverter.toString(obj));
    }

    @Test
    public void testToTimeStamp() {
	DateTime dateTime = new DateTime();
	Timestamp timeStamp = DateConverter.toTimeStamp(dateTime);
	dateTime = dateTime.withZone(DateTimeZone.UTC);

	DateTimeFormatter dtf = DateTimeFormat.forPattern("MM-dd-yyyy HH:mm:ss").withZone(DateTimeZone.UTC);

	assertEquals(dateTime.toString(dtf), DateConverter.toString(timeStamp));
    }
}
