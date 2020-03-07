<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="compAdminRequests">
	
	<h2 style="color:black"><fmt:message key="compAdminRequests"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="title"/></th>
           			<th><fmt:message key="description"/></th>
           			<th><fmt:message key="status"/></th>
           			<th><fmt:message key="username"/></th>
           			<th><fmt:message key="actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${compAdminRequests.compAdminRequestList}" var="compAdminRequest">
            <tr>
                <td>
                    <c:out value="${compAdminRequest.title}"/>
                </td>
                <td>
                    <c:out value="${compAdminRequest.description}"/>
                </td>
                <td>
                    <c:out value="${compAdminRequest.status}"/>
                </td>
                 <td>
                    <c:out value="${compAdminRequest.user.username}"/>
                </td>
                <td>
                	<spring:url value="accept/${compAdminRequest.user.username}" var='acceptCompAdminRequest'></spring:url>
                	<a href="${fn:escapeXml(acceptCompAdminRequest)}" class="btn btn-default">Accept</a>
                	
                	<spring:url value="reject/${compAdminRequest.user.username}" var='rejectCompAdminRequest'></spring:url>
                	<a href="${fn:escapeXml(rejectCompAdminRequest)}" class="btn btn-default">Reject</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
