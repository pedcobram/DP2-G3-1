<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

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

    <h2 style="color:black">Contrato de: <c:out value="${contractPlayer.player.firstName} ${contractPlayer.player.lastName}"/></h2>

    <table class="table table-striped">
        <tr>
            <th>Jugador</th>
            <td><b><c:out value="${contractPlayer.player.firstName} ${contractPlayer.player.lastName}"/></b></td>
        </tr>
        <tr>
            <th>Contrato con:</th>
            <td><c:out value="${contractPlayer.club.name}"/></td>
        </tr>
        <tr>
            <th>Salario:</th>
            <td><c:out value="${contractPlayer.salary} €"/></td>
        </tr>
        <tr>
            <th>Inicio del Contrato:</th>
            <td><c:out value="${contractPlayer.startDate}"/></td>
        </tr>
        <tr>
            <th>Fin del Contrato:</th>
            <td><c:out value="${contractPlayer.endDate}"/></td>
        </tr>  
        <tr>
            <th>Cláusula de fin del Contrato:</th>
            <td><c:out value="${contractPlayer.clause} €"/></td>
        </tr>    
    </table>
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
