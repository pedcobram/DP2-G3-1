<%--
- menu-bar.tag
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="code" required="false" type="java.lang.String"%>

<nav class="navbar navbar-expand-sm rounded sticky-top navbar-dark text-white bg-dark">
	<!-- Home button -->
	
	<a href="#" class="navbar-brand">
		<span class="fa fa-home"></span>
		<acme:message code="a"/>
	</a>
	
	<!-- Toggle button -->
	
	<button class="navbar-toggler" type="button" data-toggle="collapse"	data-target="#mainMenu">
		<span class="fa fa-bars"></span>
	</button>

	<div id="mainMenu" class="collapse navbar-collapse">
		<jsp:doBody/>
	</div>
</nav>