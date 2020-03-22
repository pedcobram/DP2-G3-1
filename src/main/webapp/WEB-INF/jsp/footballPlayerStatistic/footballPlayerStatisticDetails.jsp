<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="compAdminRequestDetails">

    <h2><fmt:message key="code.title.footballplayerstatisticdetails"/> - <c:out value="${fps.player.firstName}"/> <c:out value = " " /> <c:out value="${fps.player.lastName}"/> </h2>


    <table class="table table-striped">
    	 <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.season"/></th>
            <td><b><c:out value="${fps.season_start}"/> - <c:out value="${fps.season_end}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.assists"/></th>
            <td><b><c:out value="${fps.assists}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.goals"/></th>
            <td><c:out value="${fps.goals}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.receivedGoals"/></th>
            <td><c:out value="${fps.received_goals}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.yellowCards"/></th>
            <td><c:out value="${fps.yellow_cards}"/></td>
        </tr>
         <tr>
            <th><fmt:message key="code.label.footballplayerstatisticdetails.redCards"/></th>
            <td><c:out value="${fps.red_cards}"/></td>
        </tr>
    </table>
    
    <!-- PREGUNTAR SI SE VA A DEJAR EDITAR/BORRAR LA PETICION
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  
	
	<c:if test="${compAdminRequest.user.username == principalUsername}">
    	<spring:url value="/competitionAdminRequest/{compAdminRequestId}/edit" var="editUrl">
        	<spring:param name="compAdminRequestId" value="${compAdminRequest.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default"><fmt:message key="code.label.footballplayerstatisticdetails.edit"/></a>

    </c:if>  
     
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
