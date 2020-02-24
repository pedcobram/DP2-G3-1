<%--
- menu-suboption.tag
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
	import="java.util.Collection,java.util.ArrayList,java.util.Map,javax.servlet.jsp.tagext.JspFragment" 
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="code" required="false" type="java.lang.String"%>
<%@attribute name="action" required="true" type="java.lang.String"%>
<%@attribute name="access" required="false" type="java.lang.String"%>
<%@ attribute name="title" required="false" rtexprvalue="true" %>

<jstl:if test="${access == null}">
	<jstl:set var="access" value="true"/>
</jstl:if>

<security:authorize access="${access}">	
	<a href="javascript: clearReturnUrl(); redirect('${action}')" class="dropdown-item">
		<acme:message code="${code}"/> 
	</a>
</security:authorize>
