<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="coachTransferRequestsSent">
	
	<h2 style="color:black"><fmt:message key="code.title.coachtransferrequestsentlist"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
       				<th><fmt:message key="code.label.coachtransferrequestsentlist.myCoach.coachName"/></th>
       				<th><fmt:message key="code.label.coachtransferrequestsentlist.myCoach.clubName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.requestedCoach.coachName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.requestedCoach.clubName"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.offer"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.salary"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.clause"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.status"/></th>
           			<th><fmt:message key="code.label.coachtransferrequestsentlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${coachTransferRequests}" var="coachTransferRequest">
            <tr>
                <td>
                	<c:out value="${coachTransferRequest.myCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.myCoach.lastName}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.myCoach.club.name}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.requestedCoach.lastName}"/>
                </td>
                <td>
                	<c:out value="${coachTransferRequest.requestedCoach.club.name}"/>
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
                	<spring:url value="sent/delete/${coachTransferRequest.id}" var='deletecoachtransferrequestsentlist'></spring:url>
                	<a href="${fn:escapeXml(deletecoachtransferrequestsentlist)}" class="btn btn-default"><fmt:message key="code.button.coachtransferrequestsentlist.delete"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
