<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<fmt:message key="code.title.footballPlayers" var="Players"/>  
	<fmt:message key="code.title.freeAgent" var="FreeAgent"/> 
	<fmt:message key="code.column.name" var="Name"/>  
	<fmt:message key="code.column.club" var="Club"/> 
	<fmt:message key="code.column.position" var="Position"/> 
	<fmt:message key="code.column.value" var="Value"/> 
	<fmt:message key="code.column.birthDate" var="BirthDate"/> 
	<fmt:message key="code.crud.registerPlayer" var="RegisterPlayer"/> 
	<fmt:message key="code.crud.signFreeAgent" var="SignPlayer"/> 

<petclinic:layout pageName="footballPlayers">

	<h2 style="color:black">${Players}</h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th>${Name}</th>
           			<th>${Club}</th>
           			<th>${Position}</th>
           			<th>${Value}</th>
           			<th>${BirthDate}</th>
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
                	<c:choose>
                    	<c:when test="${footballPlayer.club == null}">
                        	<c:out value="${FreeAgent}"/>
                    	</c:when>
                    	<c:otherwise>
                        	<c:out value="${footballPlayer.club.name}"/>
                    	</c:otherwise>
                	</c:choose>       
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
    
    	<c:if test="${thisClubPresidentUsername == principalUsername && thisClubStatus == false}">
    		<spring:url value="/footballPlayer/new" var="footballPlayersNewUrl"></spring:url>
    		<a href="${fn:escapeXml(footballPlayersNewUrl)}" class="btn btn-default">${RegisterPlayer}</a>
    	</c:if> 
</petclinic:layout>
