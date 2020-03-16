<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="compAdminRequests">
	
	<h2 style="color:black"><fmt:message key="refereeRequestList"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="titleRefereeRequestList"/></th>
           			<th><fmt:message key="statusRefereeRequestList"/></th>
           			<th><fmt:message key="usernameRefereeRequestList"/></th>
           			<th><fmt:message key="matchTitleRefereeRequestList"/></th>
           			<th><fmt:message key="actionsRefereeRequestList"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${matchRefereeRequests}" var="matchRefereeRequest">
            <tr>
                <td>
                    <c:out value="${matchRefereeRequest.title}"/>
                </td>
                <td>
                    <c:out value="${matchRefereeRequest.status}"/>
                </td>
                <td>
                    <c:out value="${matchRefereeRequest.referee.user.username}"/>
                </td>
                <td>
                    <c:out value="${matchRefereeRequest.match.title}"/>
                </td>
                <td>
                	<spring:url value="accept/${matchRefereeRequest.referee.user.username}/${matchRefereeRequest.match.id}" var='acceptRefereeRequestList'></spring:url>
                	<a href="${fn:escapeXml(acceptRefereeRequestList)}" class="btn btn-default"><fmt:message key="acceptRefereeRequestList"/></a>
                	
                	<spring:url value="reject/${matchRefereeRequest.referee.user.username}/${matchRefereeRequest.match.id}" var='rejectRefereeRequestList'></spring:url>
                	<a href="${fn:escapeXml(rejectRefereeRequestList)}" class="btn btn-default"><fmt:message key="rejectRefereeRequestList"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
