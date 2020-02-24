<%--
- format.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty"
	   import="java.util.Locale, java.text.MessageFormat"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%-- Consult https://docs.oracle.com/javase/7/docs/api/java/text/MessageFormat.html --%>
 
<%@attribute name="value" required="true" type="java.lang.Object"%>
<%@attribute name="format" required="true" type="java.lang.String"%>

<%
	Object value;
	String format;
	Locale locale;
	MessageFormat formatter;
	String text;
	
	value = jspContext.getAttribute("value");
	format = (String) jspContext.getAttribute("format");
	locale = response.getLocale();
	formatter = new MessageFormat(format, response.getLocale());
	text = formatter.format(new Object[] {value});	
	jspContext.setAttribute("text", text);
%>

<jstl:out value="${text}"/>
