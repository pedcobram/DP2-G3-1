<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

<petclinic:layout pageName="footballCLubs">

    <h2 style="color:black">MY CLUB</h2>
    
    <!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

    <table class="table table-striped">
        <tr>
            <th>Name</th>
            <td><img width=30px height= auto hspace="20"; src="${footballClub.crest}"/>
                	<b><c:out value="${footballClub.name}"/></b></td>
        </tr>
        <tr>
            <th>City</th>
            <td><c:out value="${footballClub.city}"/></td>
        </tr>
        <tr>
            <th>Stadium</th>
            <td><c:out value="${footballClub.stadium}"/></td>
        </tr>
        <tr>
            <th>Foundation Date</th>
            <td><c:out value="${footballClub.foundationDate}"/></td>
        </tr>
        <tr>
            <th>Fans</th>
            <td><c:out value="${footballClub.fans}"/></td>
        </tr>
         <tr>
            <th>Coach</th>
            <td><c:out value="${footballClub.coach}"/></td>
        </tr>
         <tr>
            <th>President</th>
            <td><c:out value="${footballClub.president.firstName} ${footballClub.president.lastName}"/></td>
        </tr>
         <tr>
            <th>Money</th>
            <td><c:out value="${footballClub.money}"/></td>
        </tr>
    </table>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %--> 

	<c:if test="${footballClub.president.user.username == principalUsername}">
    	<spring:url value="/myfootballClubs/${principalUsername}/edit" var="editUrl">
		   	<spring:param name="footballClubId" value="${footballClub.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Club</a>
    </c:if>  
    
    <security:authorize access="hasAnyAuthority('president')">
        <spring:url value="/footballClub/delete" var="addUrl"></spring:url>
    	<a href="${fn:escapeXml(addUrl)}" onclick="return confirm('ARE YOU SURE?')" class="btn btn-default">Delete Club</a>
    </security:authorize>

    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
