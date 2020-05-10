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

<petclinic:layout pageName="calendary">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	
<h2 style="color:black">Calendario de la competicion:</h2>
	
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th class="th-center">JORNADAS</th>
           			
 				</tr>
        	</thead>
        	<tbody>
    <c:forEach items="${jornadas}" var="jornada">
            <tr>       
            	<td class="th-center">       		
             		<spring:url value="/competitions/{competitionId}/calendary/jornada/{jornadaId}" var="jornadaUrl">
                        <spring:param name="competitionId" value="${jornada.calendary.competition.id}"/>
                        <spring:param name="jornadaId" value="${jornada.id}"/>
                	</spring:url>
                	<a href="${fn:escapeXml(jornadaUrl)}"><b><c:out value="${jornada.name}"/></b></a>
                </td>
 
            </tr>
        </c:forEach>
    </tbody>
        </table>
 </jsp:body> 
</petclinic:layout>
