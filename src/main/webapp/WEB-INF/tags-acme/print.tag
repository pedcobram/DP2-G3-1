<%--
- message.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty" trimDirectiveWhitespaces="true"
	import="java.util.Collection, java.util.Map, java.util.HashMap,acme.framework.helpers.ConversionHelper"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="var" required="false" type="java.lang.String"%>
<%@attribute name="value" required="true" type="java.lang.Object"%>

<%
	String variable;
	Object value;
	String text;	
	
	variable = (String)jspContext.getAttribute("var");
	value = jspContext.getAttribute("value");
	text = ConversionHelper.convert(value, String.class);
	if (text == null)
		text = "";
	else 
		text = text.trim();
	jspContext.setAttribute("text", text);
	if (variable != null)
		request.setAttribute(variable, text);
%>

<jstl:if test="${var == null}">
	<jstl:out value="${text}"/>
</jstl:if>

