<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

	<fmt:message key="code.title.footballPlayer" var="Player"/>  
	<fmt:message key="code.title.freeAgent" var="FreeAgent"/> 
	<fmt:message key="code.label.name" var="Name"/>  
	<fmt:message key="code.label.age" var="Age"/> 
	<fmt:message key="code.label.team" var="Team"/>
	<fmt:message key="code.label.position" var="Position"/> 
	<fmt:message key="code.label.value" var="Value"/> 
	<fmt:message key="code.crud.sign" var="Fichar"/> 
	<fmt:message key="code.crud.contract" var="Contrato"/> 

<petclinic:layout pageName="footballPlayers">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	
    <h2 style="color:black">${Player}</h2>

    <table class="table table-striped">
        <tr>
            <th>${Name}</th>
            <td><b><c:out value="${footballPlayer.firstName} ${footballPlayer.lastName}"/></b></td>
        </tr>
        <tr>
            <th>${Age}</th>
            <td><c:out value="${footballPlayer.birthDate} "/><b><c:out value="(${footballPlayerAge})"/></b></td>
        </tr>
        <tr>
            <th>${Team}</th>
            <td>
            
             <c:choose>
                    <c:when test="${footballPlayer.club == null}">
                        <c:out value="${FreeAgent}"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${footballPlayer.club.name}"/>
                    </c:otherwise>
                </c:choose>
                       
            </td>
        </tr>
        <tr>
            <th>${Position}</th>
            <td><c:out value="${footballPlayer.position}"/></td>
        </tr>
        <tr>
            <th>${Value}</th>
            <td><c:out value="${footballPlayer.value} €"/></td>
        </tr>     
    </table>
    
    <fmt:message key="signMouseHover" var="mousehover"/>
    
    <c:if test="${footballPlayer.club == null}">
    		<spring:url value="/contractPlayer/{footballPlayerId}/new" var="newContractUrl">
    			<spring:param name="footballPlayerId" value="${footballPlayer.id}"/>
    		</spring:url>	
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(newContractUrl)}" class="btn btn-default">${Fichar}</a>
    </c:if> 
    	
		<c:if test="${footballPlayer.club.president.user.username == principalUsername}">
    		<spring:url value="/contractPlayer/{footballPlayerId}" var="contractPlayerUrl">
    			<spring:param name="footballPlayerId" value="${footballPlayer.id}"/></spring:url>
    		<a href="${fn:escapeXml(contractPlayerUrl)}" class="btn btn-default">${Contrato}</a>
    	</c:if> 
    

 </jsp:body> 
</petclinic:layout>
