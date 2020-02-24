<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="presidents">

    <h2>President Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${president.firstName} ${president.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${president.email}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${president.dni}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${president.telephone}"/></td>
        </tr>
    </table>
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  

	<c:if test="${president.user.username == principalUsername}">
    	<spring:url value="{presidentId}/edit" var="editUrl">
        	<spring:param name="presidentId" value="${president.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit President</a>
    </c:if>  
    
    <security:authorize access="hasAnyAuthority('president')">
        <spring:url value="/footballClub/new" var="addUrl"></spring:url>
    	<a href="${fn:escapeXml(addUrl)}" class="btn btn-default">Add New FootballClub</a>
    </security:authorize>
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
