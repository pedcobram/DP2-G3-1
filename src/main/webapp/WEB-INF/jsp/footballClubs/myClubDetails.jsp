<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

		<fmt:message key="code.title.myFootballClub" var="myFootballClub"/>
		<fmt:message key="code.title.contractsPlayers" var="contracts"/>
		<fmt:message key="code.label.name" var="Name"/>
    	<fmt:message key="code.label.logo" var="Logo"/>
    	<fmt:message key="code.label.city" var="City"/>
    	<fmt:message key="code.label.stadium" var="Stadium"/>
    	<fmt:message key="code.label.money" var="Money"/>
    	<fmt:message key="code.label.foundationDate" var="FoundationDate"/>
    	<fmt:message key="code.label.fans" var="Fans"/>
    	<fmt:message key="code.label.coach" var="Coach"/>
    	<fmt:message key="code.label.president" var="President"/>
    	<fmt:message key="code.crud.updateClub" var="updateClub"/>
    	<fmt:message key="code.crud.deleteClub" var="deleteClub"/>   	
    	<fmt:message key="code.list.playerList" var="playerList"/>
    	
    	
<petclinic:layout pageName="footballCLubs">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>
	
<jsp:body>	
    <h2 class="th-center">${myFootballClub}</h2>
    
    <c:if test="${!footballClub.crest.isEmpty()}">
    		<div style="margin:2%" class="col-12 text-center">
    			<img width=144px  height=144px src="<spring:url value="${footballClub.crest}" htmlEscape="true" />"/>
			</div>
    	</c:if>
    
    <!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Creo variables para las "label" con los mensajes internacionalizados (NO ES NECESARIO) %-->
	
 
    

    <table class="table table-striped">
        <tr>
            <th>${Name}</th>
            <td><b><c:out value="${footballClub.name}"/></b></td>
        </tr>
        <tr>
            <th>${City}</th>
            <td><c:out value="${footballClub.city}"/></td>
        </tr>
        <tr>
            <th>${Stadium}</th>
            <td><c:out value="${footballClub.stadium}"/></td>
        </tr>
        <tr>
            <th>${FoundationDate}</th>
            <td><c:out value="${footballClub.foundationDate}"/></td>
        </tr>
        <tr>
            <th>${Fans}</th>
            <td><c:out value="${footballClub.fans}"/></td>
        </tr>
         <tr>
            <th>${Coach}</th>
            <td><c:out value="${footballClub.coach}"/></td>
        </tr>
         <tr>
            <th>${President}</th>
            <td><c:out value="${footballClub.president.firstName} ${footballClub.president.lastName}"/></td>
        </tr>
         <tr>
            <th>${Money}</th>
            <td><c:out value="${footballClub.money}"/> €</td>
        </tr>
    </table>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %--> 

	<fmt:message key="publishClubMouseHover" var="mousehover"/>
	
	<security:authorize access="hasAnyAuthority('president')">
		<c:if test="${footballClub.president.user.username == principalUsername}">
    		<spring:url value="/myfootballClub/${principalUsername}/edit" var="editUrl">
		   		<spring:param name="footballClubId" value="${footballClub.id}"/>
    		</spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(editUrl)}" class="btn btn-default">${updateClub}</a>  	
    	
    		<spring:url value="/footballClub/${footballClub.id}/footballPlayers" var="footballPlayersUrl">
    			<spring:param name="presidentUsername" value="${footballClub.president.user.username}"/>
    		</spring:url>
    		<a   href="${fn:escapeXml(footballPlayersUrl)}" class="btn btn-default"><span class="glyphicon glyphicon-user"></span> ${playerList}</a>
    
    		<spring:url value="/contractPlayer/list" var="contractPlayersUrl"></spring:url>
    		<a   href="${fn:escapeXml(contractPlayersUrl)}" class="btn btn-default"><span class="glyphicon glyphicon-inbox"></span> ${contracts}</a>
    
    		<spring:url value="/myfootballClub/delete" var="addUrl"></spring:url>
    		<a href="${fn:escapeXml(addUrl)}" onclick="return confirm('ARE YOU SURE?')" class="btn btn-default2"><span class="glyphicon glyphicon-trash"></span> ${deleteClub}</a>
    	</c:if>      
    </security:authorize>

    <br/>
    <br/>
    <br/>
 </jsp:body>   
</petclinic:layout>
