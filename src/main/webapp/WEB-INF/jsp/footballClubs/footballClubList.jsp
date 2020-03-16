<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


		<fmt:message key="code.title.footballTeams" var="footballTeams"/>
    	<fmt:message key="code.column.city" var="City"/>
    	<fmt:message key="code.column.stadium" var="Stadium"/>
    	<fmt:message key="code.column.coach" var="Coach"/>
    	<fmt:message key="code.column.teams" var="Teams"/>
    	<fmt:message key="code.column.foundationDate" var="FoundationDate"/>


<petclinic:layout pageName="footballClubs">
	
	<h2 style="color:black">${footballTeams}</h2>
	
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th class="th-center">${Teams}</th>
           			<th>${City}</th>
           			<th>${Stadium}</th>          		
           			<th>${FoundationDate}</th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${footballClubs}" var="footballClub">
            <tr>              		
                <td>
                <spring:url value="/footballClub/{footballClubId}" var="footballClubUrl">
                        <spring:param name="footballClubId" value="${footballClub.id}"/>
                </spring:url>
                <img width=30px height= auto hspace="20"; src="${footballClub.crest}"/>
                	<a href="${fn:escapeXml(footballClubUrl)}"><b><c:out value="${footballClub.name}"/></b></a>
                </td>
                <td>
                    <c:out value="${footballClub.city}"/>
                </td>
                <td>
                    <c:out value="${footballClub.stadium}"/>
                </td>        
                <td>
                    <c:out value="${footballClub.foundationDate}"/>
                </td>
              
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
