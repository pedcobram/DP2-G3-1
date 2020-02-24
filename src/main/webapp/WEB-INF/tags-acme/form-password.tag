<%--
- form-password.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty" import="acme.framework.helpers.PasswordHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="readonly" required="false" type="java.lang.Boolean"%>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false"/>
</jstl:if>

<div class="form-group">
	<label for="${path}"> 
		<acme:message code="${code}"/>
	</label>
	<input 
		id="${path}" 
		name="${path}"
		value="<jstl:out value="${requestScope[path]}"/>"
		type="password" 
		class="form-control"
		<jstl:if test="${readonly}">
       		readonly
       	</jstl:if>
	/>
	<acme:form-errors path="${path}"/>	
</div>
