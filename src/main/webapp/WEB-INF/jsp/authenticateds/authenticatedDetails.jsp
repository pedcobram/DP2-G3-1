<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="authenticateds">

    <h2>Authenticated Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${authenticated.firstName} ${authenticated.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Email</th>
            <td><c:out value="${authenticated.email}"/></td>
        </tr>
        <tr>
            <th>DNI</th>
            <td><c:out value="${authenticated.dni}"/></td>
        </tr>
        <tr>
            <th>Telephone</th>
            <td><c:out value="${authenticated.telephone}"/></td>
        </tr>
    </table>
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  

	<c:if test="${authenticated.user.username == principalUsername}">
    	<spring:url value="{authenticatedId}/edit" var="editUrl">
        	<spring:param name="authenticatedId" value="${authenticated.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Profile</a>
    </c:if>  
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
