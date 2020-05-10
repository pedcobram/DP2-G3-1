<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<fmt:message key="code.title.coachs" var="CoachsTitle"/>  
	<fmt:message key="code.title.freeAgent" var="FreeAgent"/> 
	<fmt:message key="code.column.name" var="Name"/>  
	<fmt:message key="code.column.club" var="Club"/> 
	<fmt:message key="code.column.birthDate" var="BirthDate"/> 
	<fmt:message key="code.crud.registerCoach" var="RegisterCoach"/> 
	<fmt:message key="code.crud.signFreeAgent" var="SignCoach"/> 
	<fmt:message key="code.information.registerCoach" var="RegisterCoachInfo"/>
	<fmt:message key="code.buttom.coachlist.actions" var="Actions"/>

<petclinic:layout pageName="coachs">

	<h2 style="color:black">${CoachsTitle}</h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th>${Name}</th>
           			<th>${Club}</th>
           			<th>${BirthDate}</th>
           			<th>${Actions}</th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${coachs}" var="coach">
            <tr>
                 		
                <td>
                <spring:url value="/coachs/{coachId}" var="coachUrl">
                        <spring:param name="coachId" value="${coach.id}"/>
                </spring:url>
					<b><a href="${fn:escapeXml(coachUrl)}"><c:out value="${coach.firstName} ${coach.lastName}"/></a></b>
                </td>
                <td>              
                	<c:choose>
                    	<c:when test="${coach.club == null}">
                        	<c:out value="${FreeAgent}"/>
                    	</c:when>
                    	<c:otherwise>
                        	<c:out value="${coach.club.name}"/>
                    	</c:otherwise>
                	</c:choose>       
                </td>
              
                <td>
                    <c:out value="${coach.birthDate}"/>
                </td>
                <td>
                	<spring:url value="/transfers/coaches/request/{coachId}" var="requestTransfer">		
                		<spring:param name="coachId" value="${coach.id}"/>
                	</spring:url>
    				<a href="${fn:escapeXml(requestTransfer)}" class="btn btn-default2" style="color:white"><b><fmt:message key="code.label.coachlist.transferCoach"/></b></a>
                </td>
                
              	
            </tr>
        </c:forEach>
        </tbody>
        </table>     
   
</petclinic:layout>
