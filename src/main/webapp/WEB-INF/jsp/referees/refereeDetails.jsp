<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="referees">

    <h2><fmt:message key="code.title.refereedetails"/></h2>

    <table class="table table-striped">
        <tr>
            <th><fmt:message key="code.label.refereedetails.name"/></th>
            <td><b><c:out value="${referee.firstName} ${referee.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.refereedetails.email"/></th>
            <td><c:out value="${referee.email}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.refereedetails.dni"/></th>
            <td><c:out value="${referee.dni}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.refereedetails.telephone"/></th>
            <td><c:out value="${referee.telephone}"/></td>
        </tr>
    </table>
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  

	<c:if test="${referee.user.username == principalUsername}">
    	<spring:url value="/myRefereeProfile/edit" var="editUrl">  	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="code.button.refereedetails.edit"/></a>
    	
    	
    	<spring:url value="/referee/delete" var="editUrl"></spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default2" style="color:white"><b><fmt:message key="code.button.refereedetails.delete"/></b></a>
    	
    </c:if>  
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
