<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="error">

	<div class="col-12 text-center" >
    	<spring:url value="/resources/images/err.png" var="petsImage"/>
    	<img src="${petsImage}"/>

    	<h2><fmt:message key="oups"/></h2>

    	<p>${exception.message}</p>
     </div>

</petclinic:layout>
