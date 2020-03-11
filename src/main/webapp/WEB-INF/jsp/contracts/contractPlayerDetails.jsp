<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<fmt:message key="code.label.salary" var="salario1"/> 
	<fmt:message key="code.label.startDate" var="startfecha"/> 
	<fmt:message key="code.label.endDate" var="endfecha"/> 
	<fmt:message key="code.label.clause" var="clausula1"/>
	<fmt:message key="code.label.player" var="player"/>
	<fmt:message key="code.label.contract" var="contractWith"/> 
	<fmt:message key="code.title.contractPlayer" var="contractPl"/> 

<petclinic:layout pageName="contractPlayer">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	

    <h2 style="color:black">${contractPl} <c:out value="${contractPlayer.player.firstName} ${contractPlayer.player.lastName}"/></h2>

    <table class="table table-striped">
        <tr>
            <th>${player}</th>
            <td><b><c:out value="${contractPlayer.player.firstName} ${contractPlayer.player.lastName}"/></b></td>
        </tr>
        <tr>
            <th>${contractWith}</th>
            <td><c:out value="${contractPlayer.club.name}"/></td>
        </tr>
        <tr>
            <th>${salario1}</th>
            <td><c:out value="${contractPlayer.salary} €"/></td>
        </tr>
        <tr>
            <th>${startfecha}</th>
            <td><c:out value="${contractPlayer.startDate}"/></td>
        </tr>
        <tr>
            <th>${endfecha}</th>
            <td><c:out value="${contractPlayer.endDate}"/></td>
        </tr>  
        <tr>
            <th>${clausula1}</th>
            <td><c:out value="${contractPlayer.clause} €"/></td>
        </tr>    
    </table>
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
