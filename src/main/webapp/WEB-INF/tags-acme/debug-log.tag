<%--
- debug-log.tag
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
	   import="org.apache.commons.logging.Log, org.apache.commons.logging.LogFactory" %>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="message" required="true" type="java.lang.String"%>

<%
	Log log;

	log = LogFactory.getLog(getClass());
	log.error((String)jspContext.getAttribute("message"));
%>
