<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="footballPlayers">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	
    <h2 style="color:black"><fmt:message key="footballPlayer"/></h2>
    
    <!-- Tomo el valor del nombre de usuario actual %-->
    
    <security:authorize access="isAuthenticated()">
   		<security:authentication var="principalUsername" property="principal.username" /> 
	</security:authorize>

    <table class="table table-striped">
        <tr>
            <th><fmt:message key="nameLabel"/></th>
            <td><b><c:out value="${footballPlayer.firstName} ${footballPlayer.lastName}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="ageLabel"/></th>
            <td><c:out value="${footballPlayer.birthDate} "/><b><c:out value="(${footballPlayerAge})"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="teamLabel"/></th>
            <td>
            
             <c:choose>
                    <c:when test="${footballPlayer.club == null}">
                        <c:out value="Free Agent"/>
                    </c:when>
                    <c:otherwise>
                        <c:out value="${footballPlayer.club.name}"/>
                    </c:otherwise>
                </c:choose>
                       
            </td>
        </tr>
        <tr>
            <th><fmt:message key="positionLabel"/></th>
            <td><c:out value="${footballPlayer.position}"/></td>
        </tr>
        <tr>
            <th><fmt:message key="valueLabel"/></th>
            <td><c:out value="${footballPlayer.value} €"/></td>
        </tr>     
    </table>
    
    <fmt:message key="signMouseHover" var="mousehover"/>
    
    <c:if test="${footballPlayer.club == null}">
    		<spring:url value="/contract/new" var="newContractUrl"></spring:url>
    		<a data-toggle="tooltip" title="${mousehover}" href="${fn:escapeXml(newContractUrl)}" class="btn btn-default">Fichar</a>
    </c:if> 
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
