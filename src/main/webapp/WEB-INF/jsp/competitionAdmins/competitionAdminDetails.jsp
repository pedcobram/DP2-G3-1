<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="competitionAdmins">

    <h2><fmt:message key="code.title.competitionadmindetails"/></h2>

    <table class="table table-striped">
        <tr>
            <th><fmt:message key="code.label.competitionadmindetails.name"/></th>
            <td><b><c:out value="${competitionAdmin.firstName} ${competitionAdmin.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.competitionadmindetails.email"/></th>
            <td><c:out value="${competitionAdmin.email}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.competitionadmindetails.dni"/></th>
            <td><c:out value="${competitionAdmin.dni}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.competitionadmindetails.telephone"/></th>
            <td><c:out value="${competitionAdmin.telephone}"/></td>
        </tr>
    </table>
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  

	<c:if test="${competitionAdmin.user.username == principalUsername}">
    	<spring:url value="{competitionAdminId}/edit" var="editUrl">
        	<spring:param name="competitionAdminId" value="${competitionAdmin.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="code.button.competitionadmindetails.edit"/></a>
    	
    	
    	<spring:url value="/deleteCompetitionAdmin/${competitionAdmin.user.username}" var="editUrl"></spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default2" style="color:white"><b><fmt:message key="code.button.competitionadmindetails.delete"/></b></a>
    	
    </c:if>  
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
