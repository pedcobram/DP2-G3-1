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

		<fmt:message key="code.title.footballClub" var="footballClubTitle"/>
		<fmt:message key="code.label.name" var="Name"/>
    	<fmt:message key="code.label.logo" var="Logo"/>
    	<fmt:message key="code.label.city" var="City"/>
    	<fmt:message key="code.label.stadium" var="Stadium"/>
    	<fmt:message key="code.label.money" var="Money"/>
    	<fmt:message key="code.label.foundationDate" var="FoundationDate"/>
    	<fmt:message key="code.label.fans" var="Fans"/>
    	<fmt:message key="code.label.coach" var="Coach"/>
    	<fmt:message key="code.label.president" var="President"/>  	
    	<fmt:message key="code.list.playerList" var="playerList"/>

<petclinic:layout pageName="footballCLubs">

    <h2 style="color:black">${footballClubTitle}</h2>
    
    <table class="table table-striped">
        <tr>
            <th>${Name}</th>
            <td><img width=30px height= auto hspace="20" src="${footballClub.crest}"/>
                	<b><c:out value="${footballClub.name}"/></b></td>
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
            <td>
            	<spring:url value="/coachs/{coachId}" var="coachUrl">
                        <spring:param name="coachId" value="${coach.id}"/>
                </spring:url>
                <a href="${fn:escapeXml(coachUrl)}"><b><c:out value="${coach.firstName} ${coach.lastName}"/></b></a>
            </td>
        </tr>
         <tr>
            <th>${President}</th>
            <td><c:out value="${footballClub.president.firstName} ${footballClub.president.lastName}"/></td>
        </tr>       
    </table>

    <spring:url value="/footballClubs/list/${footballClubId}/footballPlayers" var="footballPlayersUrl"></spring:url>
    	<a href="${fn:escapeXml(footballPlayersUrl)}" class="btn btn-default"><span class="glyphicon glyphicon-user"></span> ${playerList}</a>
					 
	<c:if test="${footballClub.president.user.username != principalUsername && !notHasAPublishedTeam}">
     	<security:authorize access="hasAnyAuthority('president')">
      	  <spring:url value="/matchRequests/${footballClub.president.user.username}/new" var="newMatchRequest"></spring:url>
    		<a href="${fn:escapeXml(newMatchRequest)}" class="btn btn-default2"><fmt:message key="newMatchRequest"/></a>
    	</security:authorize>
    </c:if>   
    
     								 
    
</petclinic:layout>
