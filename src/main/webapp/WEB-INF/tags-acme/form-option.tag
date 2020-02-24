<%--
- form-option.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>
 
<%@attribute name="value" required="true" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="selected" required="false" type="java.lang.Boolean"%>

<jstl:if test="${selected == null}">
	<jstl:set var="selected" value="false"/>
</jstl:if>

<option value="${value}" <jstl:if test="${selected}">selected</jstl:if>>
	<acme:message code="${code}"/>
</option>
