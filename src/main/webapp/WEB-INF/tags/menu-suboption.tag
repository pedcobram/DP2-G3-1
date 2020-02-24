<%@tag language="java" body-content="empty" 
	import="java.util.Collection,java.util.ArrayList,java.util.Map,javax.servlet.jsp.tagext.JspFragment" 
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="active" required="true" rtexprvalue="true" %>
<%@ attribute name="url" required="true" rtexprvalue="true" %>
<%@ attribute name="title" required="false" rtexprvalue="true" %>

<%@attribute name="code" required="false" type="java.lang.String"%>
<%@attribute name="action" required="true" type="java.lang.String"%>
<%@attribute name="access" required="false" type="java.lang.String"%>

<jstl:if test="${access == null}">
	<jstl:set var="access" value="true"/>
</jstl:if>

<security:authorize access="${access}">	
	<a href="javascript: clearReturnUrl(); redirect('${url}')" class="dropdown-item">
		<acme:message code="${code}"/> 
	</a>
</security:authorize>

<li class="${active ? 'active' : ''}">
	
    <a href="<spring:url value="${url}" htmlEscape="true" class="dropdown-item" />"
       title="${fn:escapeXml(title)}">
        <jsp:doBody/>
    </a>
</li>
