<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="footballCLubs">

    <h2 style="color:black"><fmt:message key="footballClub"/></h2>
    
    <!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

    <table class="table table-striped">
        <tr>
            <th><fmt:message key="nameLabel"/></th>
            <td><img width=30px height= auto hspace="20"; src="${footballClub.crest}"/>
                	<b><c:out value="${footballClub.name}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="cityLabel"/></th>
            <td><c:out value="${footballClub.city}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="stadiumLabel"/></th>
            <td><c:out value="${footballClub.stadium}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="foundationDateLabel"/></th>
            <td><c:out value="${footballClub.foundationDate}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="fansLabel"/></th>
            <td><c:out value="${footballClub.fans}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="coachLabel"/></th>
            <td><c:out value="${footballClub.coach}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="presidentLabel"/></th>
            <td><c:out value="${footballClub.president.firstName} ${footballClub.president.lastName}"/></td>
        </tr>       
    </table>
    
    <spring:url value="/footballClub/${footballClubId}/footballPlayers" var="footballPlayersUrl"></spring:url>
    	<a href="${fn:escapeXml(footballPlayersUrl)}" class="btn btn-default">
    				<span class="glyphicon glyphicon-user"></span> 
    								 <fmt:message key="playerList"/></a>
    								 
	<c:if test="${footballClub.president.user.username != principalUsername}">
     	<security:authorize access="hasAnyAuthority('president')">
      	  <spring:url value="/matchRequest/${footballClub.president.user.username}/new" var="newMatchRequest"></spring:url>
    		<a href="${fn:escapeXml(newMatchRequest)}" class="btn btn-default2"><fmt:message key="newMatchRequest"/></a>
    	</security:authorize>
    </c:if>    								 
    
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
