<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<petclinic:layout pageName="contractsCommercial">

	<jsp:attribute name="customScript">
	
	<!-- Script para mostrar mensajes mousehover %-->
	
		<script>
			$(document).ready(function(){
  				$('[data-toggle="tooltip"]').tooltip();   
			});
		</script>
	</jsp:attribute>

<jsp:body>	

    <h2 style="color:black">Contrato</h2>

    <table class="table table-striped">
        <tr>
            <th>Banner publicitario:</th>
            <td><img width=30px height= auto hspace="20" src="${contractCommercial.publicity}"/></td>
        </tr>
        <tr>
            <th>Contrato con:</th>
            <td><c:out value="${contractCommercial.club.name}"/></td>
        </tr>
        <tr>
            <th>Salario:</th>
            <td><c:out value="${contractCommercial.money}"/></td>
        </tr>
        <tr>
            <th>Inicio del Contrato:</th>
            <td><c:out value="${contractCommercial.startDate}"/></td>
        </tr>
        <tr>
            <th>Fin del Contrato:</th>
            <td><c:out value="${contractCommercial.endDate}"/></td>
        </tr>  
        <tr>
            <th>Cláusula de fin del Contrato:</th>
            <td><c:out value="${contractCommercial.clause}"/></td>
        </tr>    
    </table>
 
    <c:choose>
		<c:when test="${not empty footballClub}">
			<c:choose>
				<c:when test="${empty contractCommercial.club}">
					<c:choose>
						<c:when test="${hasAlreadyContract}">
							<p style="color:red">No puedes tener más de un contrato a la vez.</p>
						</c:when>
						<c:otherwise>
							<spring:url value="/contractsCommercial/{contractCommercialId}/addToMyClub" var="addContractToClubURL">
					   		<spring:param name="contractCommercialId" value="${contractCommercial.id}"/>
					   		</spring:url>	
				   			<a data-toggle="tooltip" href="${fn:escapeXml(addContractToClubURL)}" class="btn btn-default">Añadir Contrato Publicitario</a>
						</c:otherwise>
					</c:choose>
				</c:when>
				<c:otherwise>
					<c:choose>
						<c:when test="${footballClub.id eq contractCommercial.club.id}">
							<spring:url value="/contractsCommercial/{contractCommercialId}/removeFromMyClub" var="removeContractFromClubURL">
					   			<spring:param name="contractCommercialId" value="${contractCommercial.id}"/>
					   		</spring:url>	
					   			<a data-toggle="tooltip" href="${fn:escapeXml(removeContractFromClubURL)}" class="btn btn-default">Eliminar Contrato Publicitario</a>
						</c:when>
						<c:otherwise>
							<p style="color:red">Este contrato publicitario ya esta comprado</p>
						</c:otherwise>
					</c:choose>
				</c:otherwise>
			</c:choose>
		</c:when>    
		<c:otherwise>
			<p style="color:red">Para poder adquirir un contrato publicitario primero necesitas un Club</p>
		</c:otherwise>
	</c:choose>
    
    <br/>
    <br/>
    <br/>
 </jsp:body> 
</petclinic:layout>
