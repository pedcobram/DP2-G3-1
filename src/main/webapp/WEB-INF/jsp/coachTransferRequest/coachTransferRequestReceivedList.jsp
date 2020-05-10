<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="coachTransferRequestsReceived">
	
	<fmt:message key="code.message.coachtransferrequest.acceptConfirmation" var="confirmationMessage" /> 
	<fmt:message key="code.message.coachtransferrequest.deleteConfirmation" var="deleteMessage" /> 
	
	<h2 style="color:black"><fmt:message key="code.title.coachtransferrequestreceivedlist"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.myCoach.coachName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.myCoach.clubName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.requestedCoach.coachName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.requestedCoach.clubName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.offer"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.salary"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.clause"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.status"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestreceivedlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${coachTransferRequests}" var="coachTransferRequest">
            <tr>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.requestedCoach.lastName}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.club.name}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.myCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.myCoach.lastName}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.myCoach.club.name}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.offer}€"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.salary}€"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.clause}€"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.status}"/>
                </td>
                 <td>                	
                	 <spring:url value="/transfers/coaches/requests/received/accept/${coachTransferRequest.id}" var='acceptcoachtransferrequestsentlist'></spring:url>
                	<a href="${fn:escapeXml(acceptcoachtransferrequestsentlist)}" onclick="return confirm('${confirmationMessage}')" class="btn btn-default"><fmt:message key="code.button.playertransferrequestsentlist.accept"/></a>
                	
                	<spring:url value="/transfers/coaches/requests/received/reject/${coachTransferRequest.id}" var='rejectcoachtransferrequestsentlist'></spring:url>
                	<a href="${fn:escapeXml(rejectcoachtransferrequestsentlist)}" onclick="return confirm('${deleteMessage}')" class="btn btn-default"><fmt:message key="code.button.playertransferrequestsentlist.reject"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
