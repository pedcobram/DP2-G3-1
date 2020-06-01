<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="presidentRequests">
	
	<h2 style="color:black"><fmt:message key="code.title.presidentrequestlist"/></h2>
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
        <c:forEach items="${presidentRequests}" var="presidentRequest">
            <tr>
                <td>
                    <c:out value="${presidentRequest.title}"/>
                </td>
                <td>
                    <c:out value="${presidentRequest.description}"/>
                </td>
                <td>
                    <c:out value="${presidentRequest.status}"/>
                </td>
                 <td>
                    <c:out value="${presidentRequest.user.username}"/>
                </td>
                <td>
                	<spring:url value="accept/${presidentRequest.user.username}" var='acceptPresidentRequest'></spring:url>
                	<a href="${fn:escapeXml(acceptPresidentRequest)}" class="btn btn-default"><fmt:message key="code.button.compadminrequestlist.accept"/></a>
                	
                	<spring:url value="reject/${presidentRequest.user.username}" var='rejectPresidentRequest'></spring:url>
                	<a href="${fn:escapeXml(rejectPresidentRequest)}" class="btn btn-default"><fmt:message key="code.button.compadminrequestlist.reject"/></a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
