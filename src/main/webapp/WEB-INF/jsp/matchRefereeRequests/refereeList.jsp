<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="Matches">
	
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<h2 style="color:black"><fmt:message key="refereeList"/></h2>
	<h2>Match ID: <c:out value="${matchId}" /> </h2>

 	<table id="matchRefereeRequestTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="nameRefereeList"/></th>
           			<th><fmt:message key="dniRefereeList"/></th>
           			<th><fmt:message key="emailRefereeList"/></th>
           			<th><fmt:message key="tlfhRefereeList"/></th>
           			<th><fmt:message key="usernameRefereeList"/></th>
           			<th><fmt:message key="actionsRefereeList"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${referees}" var="referees">
            <tr>
                <td>
                    <c:out value="${referees.firstName}"/>, <c:out value="${referees.lastName}"/> 
                </td>
                <td>
                    <c:out value="${referees.dni}"/>
                </td>
                <td>
                    <c:out value="${referees.email}"/>
                </td>
                <td>
                    <c:out value="${referees.telephone}"/>
                </td>
                 <td>
                    <c:out value="${referees.user.username}"/> 
                </td>
                <td>
                	
                  		<spring:url value="/matches/refereeRequest/new/${matchId}/${referees.id}" var='requestRefereeList'></spring:url>
                		<a href="${fn:escapeXml(requestRefereeList)}" class="btn btn-default"><fmt:message key="requestRefereeList"/></a>
                	
                </td>
            </tr>
        </c:forEach>
        </tbody>
     </table>
</petclinic:layout>
