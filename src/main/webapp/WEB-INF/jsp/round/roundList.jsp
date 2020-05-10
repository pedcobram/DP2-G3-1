<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

		
		<fmt:message key="code.label.round.rounds" var="rondas"/>
		<fmt:message key="code.button.back" var="back"/>
    	

<!-- Tomo el valor del nombre de usuario actual 
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>%-->

<petclinic:layout pageName="rounds">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	

<h2 style="color:black" >${competition.name}</h2>
	
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th class="th-center">${rondas}</th>
           			
 				</tr>
        	</thead>
        	<tbody>
    <c:forEach items="${rounds}" var="round">
            <tr>       
            	<td class="th-center">       		
             		<spring:url value="/competitions/{competitionId}/round/{roundId}" var="roundUrl">
                        <spring:param name="competitionId" value="${round.competition.id}"/>
                        <spring:param name="roundId" value="${round.id}"/>
                	</spring:url>
                	<a href="${fn:escapeXml(roundUrl)}"><b><c:out value="${round.name}"/></b></a>
                </td>
 
            </tr>
        </c:forEach>
    </tbody>
        </table>
         <input type="button" class="btn btn-default" value="${back}" name="Back" onclick="history.back()" />           
        
 </jsp:body> 
</petclinic:layout>
