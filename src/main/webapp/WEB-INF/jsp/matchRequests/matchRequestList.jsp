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
	
	<c:if test="${receivedRequests == false}">
		<h2 style="color:black"><fmt:message key="code.title.matchrequestlist.received"/></h2>
	</c:if>
	<c:if test="${receivedRequests == true}">
		<h2 style="color:black"><fmt:message key="code.title.matchrequestlist.sent"/></h2>
	</c:if>
 		<table id="matchRequestsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.matchrequestlist.title"/></th>
           			<th><fmt:message key="code.label.matchrequestlist.matchdate"/></th>
           			<th><fmt:message key="code.label.matchrequestlist.status"/></th>
           			<th><fmt:message key="code.label.matchrequestlist.footballclub.one"/></th>
           			<th><fmt:message key="code.label.matchrequestlist.footballclub.two"/></th>
           			<th><fmt:message key="code.label.matchrequestlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${matchRequests}" var="matchRequest">
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
                	<spring:url value="/matchRequests/delete/${matchRequest.id}" var='deleteMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(deleteMatchRequest)}" class="btn btn-default"><fmt:message key="code.button.matchrequestlist.delete"/></a>
                </c:if>   
                <c:if test="${receivedRequests == false && matchRequest.status == 'ON_HOLD'}">
                	<spring:url value="/matchRequests/accept/${matchRequest.id}" var='acceptMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(acceptMatchRequest)}" class="btn btn-default"><fmt:message key="code.button.matchrequestlist.accept"/></a>
                </c:if>   
                <c:if test="${receivedRequests == false && matchRequest.status == 'ON_HOLD'}">
                	<spring:url value="/matchRequests/reject/${matchRequest.id}" var='rejectMatchRequest'></spring:url>
                	<a href="${fn:escapeXml(rejectMatchRequest)}" class="btn btn-default"><fmt:message key="code.button.matchrequestlist.reject"/></a>
                </c:if>        
                </td>
                
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
