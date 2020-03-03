<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="footballPlayers">
	
	<h2 style="color:black"><fmt:message key="footballPlayers"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th><fmt:message key="name"/></th>
           			<th><fmt:message key="club"/></th>
           			<th><fmt:message key="position"/></th>
           			<th><fmt:message key="value"/></th>
           			<th><fmt:message key="birthDate"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${footballPlayers}" var="footballPlayer">
            <tr>
                 		
                <td>
                <spring:url value="/footballPlayers/{footballPlayerId}" var="footballPlayerUrl">
                        <spring:param name="footballPlayerId" value="${footballPlayer.id}"/>
                </spring:url>
					<b><a href="${fn:escapeXml(footballPlayerUrl)}"><c:out value="${footballPlayer.firstName} ${footballPlayer.lastName}"/></a></b>
                </td>
                <td>
                    <c:out value="${footballPlayer.club.name}"/>
                </td>
                <td>
                    <c:out value="${footballPlayer.position}"/>
                </td>
                <td>
                    <c:out value="${footballPlayer.value} €"/>
                </td>
                <td>
                    <c:out value="${footballPlayer.birthDate}"/>
                </td>
                
              	
            </tr>
        </c:forEach>
        </tbody>
        </table>
        
        <!-- Tomo el valor del nombre de usuario actual %-->
    
    	<security:authorize access="isAuthenticated()">
   			<security:authentication var="principalUsername" property="principal.username" /> 
		</security:authorize>
    
    	<c:if test="${param.presidentUsername == principalUsername}">
    		<spring:url value="/footballPlayer/new" var="footballPlayersNewUrl"></spring:url>
    		<a href="${fn:escapeXml(footballPlayersNewUrl)}" class="btn btn-default"><fmt:message key="createPlayer"/></a>
    	or
    	</c:if> 
    	
    	<c:if test="${param.presidentUsername == principalUsername}">
    		<spring:url value="/footballPlayers/freeAgent" var="footballPlayersNewUrl"></spring:url>
    		<a href="${fn:escapeXml(footballPlayersNewUrl)}" class="btn btn-default">Sign a Free Agent</a>
    	</c:if> 
</petclinic:layout>
