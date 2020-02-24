<%--
- alert-success.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" import="java.util.Collection, java.util.Map, java.util.HashMap"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<div class="alert alert-success">
	<button type="button" class="close" data-dismiss="alert">
		<span class="fa fa-times"></span>
	</button>
	<jsp:doBody/>	
</div>
