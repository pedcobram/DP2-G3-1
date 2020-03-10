<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="matchRequests">
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<c:if test="${receivedRequests == true}">
		<h2 style="color:black"><fmt:message key="receivedMatchRequests"/></h2>
	</c:if>
	<c:if test="${receivedRequests == false}">
		<h2 style="color:black"><fmt:message key="sentMatchRequests"/></h2>
	</c:if>
 		<table id="matchRequestsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="titleMatchRequestList"/></th>
           			<th><fmt:message key="matchDateMatchRequestList"/></th>
           			<th><fmt:message key="statusMatchRequestList"/></th>
           			<th><fmt:message key="footballClub1MatchRequestList"/></th>
           			<th><fmt:message key="footballClub2MatchRequestList"/></th>
           			<th><fmt:message key="actionsMatchRequestList"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${matchRequests.matchRequestList}" var="matchRequest">
            <tr>
                <td>
                    <c:out value="${matchRequest.title}"/>
                </td>
                <td>
                    <c:out value="${matchRequest.matchDate}"/>
                </td>
                <td>
                    <c:out value="${matchRequest.status}"/>
                </td>
                 <td>
                    <c:out value="${matchRequest.footballClub1.name}"/> 
                </td>
                <td>
                    <c:out value="${matchRequest.footballClub2.name}"/>
                </td>
                <td>
               	<c:if test="${matchRequest.footballClub1.president.user.username == principalUsername}">
                	<spring:url value="delete/${matchRequest.id}" var='deleteMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(deleteMatchRequest)}" class="btn btn-default"><fmt:message key="deleteMatchRequestList"/></a>
                </c:if>   
                <c:if test="${receivedRequests == false}">
                	<spring:url value="accept/${matchRequest.id}" var='acceptMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(acceptMatchRequest)}" class="btn btn-default"><fmt:message key="acceptMatchRequestList"/></a>
                </c:if>   
                <c:if test="${receivedRequests == false}">
                	<spring:url value="reject/${matchRequest.id}" var='rejectMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(rejectMatchRequest)}" class="btn btn-default"><fmt:message key="rejectMatchRequestList"/></a>
                </c:if>        
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
