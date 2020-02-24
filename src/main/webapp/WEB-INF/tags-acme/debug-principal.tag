<%--
- debug-principal.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty" import="acme.framework.helpers.PrincipalHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="alert alert-info" style="font-family: monospace;">
	<jstl:set var="principal" value="${PrincipalHelper.get()}"/>
 	<h2>Principal</h2>
 	<dl>
 		<dt>Username:</dt>
 		<dd style="margin-left: 2em"><jstl:out value="${principal.getUsername()}"/></dd>
 		<dt>Active role:</dt>
 		<dd style="margin-left: 2em"><jstl:out value="${principal.getActiveRole().getName()}"/></dd>
 		<dt>Authorities <jstl:out value="${authority.getAuthority()}"/></dt>
 		<dd style="margin-left: 2em">
	 		<jstl:forEach var="authority" items="${principal.getAuthorities()}">
	 			<jstl:out value="${authority.getAuthority()}"/> &nbsp;	 			
	 		</jstl:forEach>
 		</dd> 		
 	</dl>
 </div>
