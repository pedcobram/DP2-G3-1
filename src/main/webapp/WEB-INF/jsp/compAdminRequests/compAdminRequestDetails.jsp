<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="compAdminRequests">

    <h2>Competition Admin Request Information</h2>


    <table class="table table-striped">
        <tr>
            <th>Title</th>
            <td><b><c:out value="${compAdminRequest.title}"/></b></td>
        </tr>
        <tr>
            <th>Description</th>
            <td><c:out value="${compAdminRequest.description}"/></td>
        </tr>
        <tr>
            <th>Status</th>
            <td><c:out value="${compAdminRequest.status}"/></td>
        </tr>
    </table>
    
    <!-- PREGUNTAR SI SE VA A DEJAR EDITAR/BORRAR LA PETICION
    
    <!-- Tomo el valor del nombre de usuario actual %-->  
    
	<security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>
	
	<!-- Muestro el botón de editar si el usuario coincide con el usuario actual %-->  
	

	
	<c:if test="${compAdminRequest.user.username == principalUsername}">
    	<spring:url value="{compAdminRequestId}/edit" var="editUrl">
        	<spring:param name="compAdminRequestId" value="${compAdminRequest.id}"/>
    	</spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default">Edit Competition Admin Request</a>
    	
    	
    	<spring:url value="/deleteCompAdminRequest" var="editUrl"></spring:url>
    	<a href="${fn:escapeXml(editUrl)}" class="btn btn-default2" style="color:white"><b><fmt:message key="deleteCompAdminRequest"/></b></a>
    	
    </c:if>  
    
     -->
     
    <br/>
    <br/>
    <br/>
    
</petclinic:layout>
