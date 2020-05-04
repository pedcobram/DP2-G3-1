<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<fmt:message key="code.button.competition.addClub" var="AddClub"/>
    	

<!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

<petclinic:layout pageName="competition">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	

    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><b><c:out value="${competition.name}"/></b></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${competition.description}"/></td>
        </tr>
         <tr>
            <th>Type</th>
            <td><c:out value="${competition.type}"/></td>
        </tr>
        <tr>
            <th>Reward</th>
            <td><c:out value="${competition.reward} €"/></td>
        </tr>
        <tr>
            <th>Admin</th>
            <td><c:out value="${competition.creator}"/></td>
        </tr>      
        
     </table>
     
     <c:if test="${(competition.creator == principalUsername) && competition.status == false}">
    		<spring:url value="/competition/{competitionId}/edit" var="editUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(editUrl)}" class="btn btn-default">Actualizar</a>

		<!--Añadir equipos  -->
    		<spring:url value="/competition/{competitionId}/footballClubs" var="clubUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(clubUrl)}" class="btn btn-default">${AddClub}</a>  	
    	</c:if> 
     
 </jsp:body> 
</petclinic:layout>
