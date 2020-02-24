<%--
- footer-option.tag
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
	import="java.util.Collection, java.util.ArrayList, java.util.Map, javax.servlet.jsp.tagext.JspFragment" 
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="icon" required="true" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="action" required="true" type="java.lang.String"%>
<%@attribute name="newTab" required="false" type="java.lang.Boolean"%>

<jstl:if test="${newTab == null}">
	<jstl:set var="newTab" value="false"/>
</jstl:if>

<li style="font-size: 80%">
	<span class="${icon}"></span>
	<jstl:choose>
		<jstl:when test="${newTab == false}">
			<a href="javascript: redirect('${action}')">
				<acme:message code="${code}"/>
			</a>
		</jstl:when>
		<jstl:otherwise>
			<a href="javascript: redirect('${action}', '_blank')">
				<acme:message code="${code}"/>
			</a>
		</jstl:otherwise>
	</jstl:choose>
	
	
</li>

