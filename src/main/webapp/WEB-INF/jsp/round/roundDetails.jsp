<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		<fmt:message key="code.label.round.matches" var="matches"/>
		<fmt:message key="code.button.back" var="back"/>
    	

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
<h2 style="color:black" >${round.competition.name}</h2>
	
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th class="th-center">${matches}</th>
           			
 				</tr>
        	</thead>
        	<tbody>
    <c:forEach items="${round.matches}" var="match">
            <tr>       
            	<td class="th-center">       		
             		<spring:url value="/competitions/{competitionId}/round/{roundId}/match/{matchId}" var="matchUrl">
             			<spring:param name="competitionId" value="${match.round.competition.id}"/>
             			<spring:param name="roundId" value="${match.round.id}"/>
                        <spring:param name="matchId" value="${match.id}"/>
                	</spring:url>
                	<a href="${fn:escapeXml(matchUrl)}"><b><c:out value="${match.footballClub1.name} vs ${match.footballClub2.name}"/></b></a>
                </td>
 
            </tr>
        </c:forEach>
    </tbody>
        </table>
        <input type="button" class="btn btn-default" value="${back}" name="Back" onclick="history.back()" />  
 </jsp:body> 
</petclinic:layout>
