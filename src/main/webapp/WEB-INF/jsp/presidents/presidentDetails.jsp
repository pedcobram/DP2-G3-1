<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

		<fmt:message key="code.crud.new" var="New"/>
		<fmt:message key="code.label.name" var="Name"/>
		<fmt:message key="code.label.lastName" var="lastName"/>
    	<fmt:message key="code.label.email" var="Email"/>
    	<fmt:message key="code.label.dni" var="Dni"/>
    	<fmt:message key="code.label.telephone" var="Telephone"/>
    	<fmt:message key="code.title.PresidentInformation" var="PresidentInformation"/>
    	<fmt:message key="code.crud.editProfile" var="Update"/>
    	<fmt:message key="code.crud.deletePresident" var="Delete"/>

<petclinic:layout pageName="presidents">

    <h2>${PresidentInformation}</h2>

    <table class="table table-striped">
        <tr>
            <th>${Name}</th>
            <td><b><c:out value="${president.firstName} ${president.lastName}"/></b></td>
        </tr>
        <tr>
            <th>${Email}</th>
            <td><c:out value="${president.email}"/></td>
        </tr>
        <tr>
            <th>${Dni}</th>
            <td><c:out value="${president.dni}"/></td>
        </tr>
        <tr>
            <th>${Telephone}</th>
            <td><c:out value="${president.telephone}"/></td>
        </tr>
    </table>
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  

	<c:if test="${president.user.username == principalUsername}">
    	<spring:url value="/presidents/{principalUsername}/edit" var="editUrl">
        	<spring:param name="principalUsername" value="${president.user.username}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">${Update}</a>
    	
    	
    	<spring:url value="/presidents/delete" var="editUrl"></spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default2" style="color:white"><b>${Delete}</b></a>
    	
    </c:if>  
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
