<%--
- form.tag
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
	import="java.util.Collection, java.util.ArrayList, java.util.Map, javax.servlet.jsp.tagext.JspFragment"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@ attribute name="action" required="false" type="java.lang.String"%>
<%@ attribute name="readonly" required="false" type="java.lang.Boolean"%>

<jstl:if test="${action == null}">
	<jstl:set var="action" value="${requestScope['javax.servlet.forward.request_uri']}"/>
</jstl:if>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false"/>
</jstl:if>

<form id="form" method="post" action="javascript: form.action = getAbsoluteUrl('${action}')">
	<acme:form-errors path="*"/>
	<acme:form-hidden path="id"/>
	<acme:form-hidden path="version"/>
	<jsp:doBody/>
	<input type="hidden" name="_csrf" value="${_csrf.token}">
</form>

<script type="text/javascript">
	$(document).ready(function() {
		if (${readonly} || $("#form :input[type=submit]").length == 0) {
			$("#form :input"). //
				not(':input[type=button], :input[type=submit], :input[type=reset], :input[type=hidden]'). //
				attr("disabled", "disabled");
			$("#form :input[type=checkbox]"). //
				attr("disabled", "disabled");			
		}		
	});
</script>


