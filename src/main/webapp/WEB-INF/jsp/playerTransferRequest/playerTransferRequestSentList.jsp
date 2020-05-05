<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="playerTransferRequestsSent">
	
	<h2 style="color:black"><fmt:message key="code.title.playertransferrequestsentlist"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.playerName"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.clubName"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.clause"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.destinationClub"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.offer"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.contractDuration"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.status"/></th>
           			<th><fmt:message key="code.label.playertransferrequestsentlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${playerTransferRequests}" var="playerTransferRequest">
            <tr>
                <td>
                	<c:out value="${playerTransferRequest.footballPlayer.firstName}"/> <c:out value=" "/> <c:out value="${playerTransferRequest.footballPlayer.lastName}"/>
                </td>
                <td>
                	<c:out value="${playerTransferRequest.footballPlayer.club.name}"/>
                </td>
                <td>
                	<c:out value="${playerTransferRequest.contract.clause}€"/>
                </td>
                <td>
                	<c:out value="${playerTransferRequest.club.name}"/>
                </td>
                <td>
					<c:out value="${playerTransferRequest.offer}€"/> 
                </td>
                <td>
                	<c:out value="${playerTransferRequest.contractTime}"/> <c:out value=" "/> <fmt:message key="code.label.playertransferrequestsentlist.years"/>
                </td>
                <td>
                	<c:out value="${playerTransferRequest.status}"/>
                </td>
                 <td>                	
                	<spring:url value="sent/delete/${playerTransferRequest.id}" var='deleteplayertransferrequestsentlist'></spring:url>
                	<a href="${fn:escapeXml(deleteplayertransferrequestsentlist)}" class="btn btn-default"><fmt:message key="code.button.playertransferrequestsentlist.delete"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
