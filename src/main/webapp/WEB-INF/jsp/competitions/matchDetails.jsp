<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

		<fmt:message key="code.title.footballClub" var="footballClubTitle"/>
		<fmt:message key="code.label.name" var="Name"/>
    	<fmt:message key="code.label.logo" var="Logo"/>
    	<fmt:message key="code.label.city" var="City"/>
    	<fmt:message key="code.label.stadium" var="Stadium"/>
    	<fmt:message key="code.label.money" var="Money"/>
    	<fmt:message key="code.label.foundationDate" var="FoundationDate"/>
    	<fmt:message key="code.label.fans" var="Fans"/>
    	<fmt:message key="code.label.coach" var="Coach"/>
    	<fmt:message key="code.label.president" var="President"/>  	
    	<fmt:message key="code.list.playerList" var="playerList"/>

<petclinic:layout pageName="matches">

    <h2 style="color:black"><fmt:message key="code.title.matchdetails"/></h2>
    
    <table class="table table-striped">
        <tr>
            <th><fmt:message key="code.label.matchdetails.title"/></th>
            <td><c:out value="${match.title}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.matchdate"/></th>
            <td><c:out value="${match.matchDate}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.stadium"/></th>
            <td><c:out value="${match.stadium}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.footballclub.one"/></th>
            <td><c:out value="${match.footballClub1.name}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.matchdetails.footballclub.two"/></th>
            <td><c:out value="${match.footballClub2.name}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.matchdetails.referee"/></th>
            <td><c:out value="${match.referee.firstName} ${match.referee.lastName}"/></td>
        </tr>  
        <tr>		
        	<th><fmt:message key="code.label.matchdetails.status"/></th>
        	<td><c:out value="${match.matchStatus}"/></td>        
        </tr> 
    </table>							 
    
</petclinic:layout>
