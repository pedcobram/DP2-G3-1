<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="error">

	<div class="col-12 text-center" >
    	<spring:url value="/resources/images/err.png" var="petsImage"/>
    	<img src="${petsImage}"/>

    	<h2>Sorry! Something happened...</h2>

    	<p>${exception.message}</p>
     </div>

</petclinic:layout>
