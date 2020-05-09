<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<fmt:message key="code.button.competition.addClub" var="addClub"/>
		<fmt:message key="code.button.competition.showClub" var="showClub"/>
    	

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
        <tr> 
        <spring:url value="/competitions/{competitionId}/clubs" var="clubUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
            <th><fmt:message key="code.label.equipos"/></th>
            <td><a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(clubUrl)}" ><c:out value="${size}"/></a></td>
        </tr>      
        
     </table>
     
     
     
     <c:if test="${(competition.creator == principalUsername) && competition.status == false}">
    		<spring:url value="/competition/{competitionId}/edit" var="editUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(editUrl)}" class="btn btn-default">Actualizar</a>

		<!--Añadir equipos  -->
    		<spring:url value="/competition/{competitionId}/addClubs" var="addClubUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(addClubUrl)}" class="btn btn-default">${addClub}</a>
    		
    	<!-- Gestionar Equipos -->  
    	
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(clubUrl)}" class="btn btn-default">${showClub}</a> 	
    		
    	<!-- Publicar Competición-->  
    		<spring:url value="/competition/{competitionId}/publish" var="publishUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(publishUrl)}" class="btn btn-default2">PUBLICAR COMPETICIÓN Y GENERAR CALENDARIO</a> 
    	</c:if> 
    	<!-- Calendario Competición-->  
    	<c:if test="${competition.status == true}">
    	<spring:url value="/competitions/{competitionId}/calendary" var="calendaryUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(calendaryUrl)}" class="btn btn-default">Calendario</a>
     	</c:if> 
     	
     	<c:if test="${(competition.creator == principalUsername)}">
    		<spring:url value="/competition/{competitionId}/delete" var="deleteUrl">
		   		<spring:param name="competitionId" value="${competition.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(deleteUrl)}" class="btn btn-default2">Borrar Competición</a>
     	</c:if>
     	
     	<c:if test="${statusError == true}">
     		<br>
     		<br>	
    		<p style="color:red"> En el formato liga deben haber mínimo 4 equipos y deben ser pares (4, 6, 8, 10...)</p>
    		<br>	
    	</c:if>
    	
    	<c:if test="${statusError2 == true}">
     		<br>
     		<br>	
    		<p style="color:red"> No puedes borrar la competición, ya se ha disputado al menos un partido</p>
    		<br>	
    	</c:if>
     	
     	
 </jsp:body> 
</petclinic:layout>
