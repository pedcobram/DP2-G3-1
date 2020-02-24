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
	import="java.util.Collection, java.util.Map, java.util.HashMap"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="var" required="false" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="arguments" required="false" type="java.lang.String[]"%>

<jstl:choose>
	<jstl:when test="${var == null}">
		<spring:message code="${code}" arguments="${arguments}"/>
	</jstl:when>
	<jstl:otherwise>
		<spring:message var="${message}" scope="request" code="${code}" arguments="${arguments}"/>		
	</jstl:otherwise>
</jstl:choose>
