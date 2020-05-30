<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="matchDateChangeRequest">
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<h2 style="color:black"><fmt:message key="code.title.matchdatechangerequestlist"/></h2>

 	<table id="matchesTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.title"/></th>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.newDate"/></th>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.reason"/></th>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.previousDate"/></th>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.status"/></th>
           			<th><fmt:message key="code.label.matchdatechangerequestlist.actions"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${matchDateChangeRequest}" var="matchdatechangerequest">
            <tr>
                <td>
                    <c:out value="${matchdatechangerequest.title}"/>
                </td>
                <td>
                    <c:out value="${matchdatechangerequest.new_date}"/>
                </td>
                <td>
                    <c:out value="${matchdatechangerequest.reason}"/>
                </td>
                <td>
                    <c:out value="${matchdatechangerequest.match.matchDate}"/>
                </td>
				<td>
                    <c:out value="${matchdatechangerequest.status}"/>
                </td>
                <td>
               		<spring:url value="/matches/date-request/delete/${matchdatechangerequest.id}" var='deleteMatchDateChangeRequest'></spring:url>
               		<a href="${fn:escapeXml(deleteMatchDateChangeRequest)}" class="btn btn-default"><fmt:message key="code.button.matchdatechangerequestlist.delete"/></a>            	         	
                </td>
            </tr>
        </c:forEach>
        </tbody>
     </table>
</petclinic:layout>
