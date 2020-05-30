<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="refereeRequests">
	
	<h2 style="color:black"><fmt:message key="code.title.refereerequestlist"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.compadminrequestlist.title"/></th>
           			<th><fmt:message key="code.label.compadminrequestlist.description"/></th>
           			<th><fmt:message key="code.label.compadminrequestlist.status"/></th>
           			<th><fmt:message key="code.label.compadminrequestlist.username"/></th>
           			<th><fmt:message key="code.label.compadminrequestlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${refereeRequests}" var="refereeRequest">
            <tr>
                <td>
                    <c:out value="${refereeRequest.title}"/>
                </td>
                <td>
                    <c:out value="${refereeRequest.description}"/>
                </td>
                <td>
                    <c:out value="${refereeRequest.status}"/>
                </td>
                 <td>
                    <c:out value="${refereeRequest.user.username}"/>
                </td>
                <td>
                	<spring:url value="accept/${refereeRequest.user.username}" var='acceptRefereeRequest'></spring:url>
                	<a href="${fn:escapeXml(acceptRefereeRequest)}" class="btn btn-default"><fmt:message key="code.button.compadminrequestlist.accept"/></a>
                	
                	<spring:url value="reject/${refereeRequest.user.username}" var='rejectRefereeRequest'></spring:url>
                	<a href="${fn:escapeXml(rejectRefereeRequest)}" class="btn btn-default"><fmt:message key="code.button.compadminrequestlist.reject"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
