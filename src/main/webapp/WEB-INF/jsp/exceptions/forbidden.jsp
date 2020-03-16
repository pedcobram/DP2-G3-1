<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:message key="code.error.forbidden" var="forbidden"/>

<petclinic:layout pageName="errorForbidden">

	<div class="col-12 text-center" >
    	<spring:url value="/resources/images/ForbiddenAccess.png" var="forbiddenImage"/>
    	<img style="margin-bottom:10px" src="${forbiddenImage}"/>

    	<h2>${forbidden}</h2>

    	<p>${exception.message}</p>
     </div>

</petclinic:layout>
