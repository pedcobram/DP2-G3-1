<%--
- menu-option.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" 
	import="java.util.Collection,java.util.ArrayList,java.util.Map,javax.servlet.jsp.tagext.JspFragment" 
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="action" required="false" type="java.lang.String"%>
<%@attribute name="access" required="false" type="java.lang.String"%>

<jstl:if test="${action == null}">
	<jstl:set var="action" value="#"/>
</jstl:if>

<jstl:if test="${access == null}">
	<jstl:set var="access" value="true"/>
</jstl:if>

<security:authorize access="${access}">
	<jsp:doBody var="body"/>
	<jstl:set var="hasBody" value="${!body.trim().equals('')}"/>	
	<jstl:choose>
		<jstl:when test="${hasBody}">
			<li class="nav-item dropdown">
				<a href="javascript: clearReturnUrl(); redirect('${action}')" class="nav-link dropdown-toggle" data-toggle="dropdown">
					<acme:message code="${code}"/> 
				</a>
				<div class="dropdown-menu ${__acme_menu_alignment}">
					${body}
				</div>
			</li>
		</jstl:when>
		<jstl:otherwise>
			<li class="${nav-item}">
				<a href="javascript: clearReturnUrl(); redirect('${action}')" class="nav-link">
					<acme:message code="${code}"/> 
				</a>
			</li>
		</jstl:otherwise>
	</jstl:choose>	
</security:authorize>
