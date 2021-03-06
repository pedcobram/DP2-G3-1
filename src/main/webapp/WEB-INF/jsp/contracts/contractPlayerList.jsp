<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>

	<fmt:message key="code.column.player" var="jugador"/>  
	<fmt:message key="code.column.salary" var="salario"/>  
	<fmt:message key="code.column.endDate" var="endfecha"/> 
	<fmt:message key="code.title.contract" var="contracts"/> 

<petclinic:layout pageName="contractPlayers">
	
	<h2 style="color:black">${contracts}</h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
            		<th>${jugador}</th>
           			<th>${salario}</th>
           			<th>${endfecha}</th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${contractPlayers}" var="contractPlayer">
            <tr>
                <td>
                <spring:url value="/contractPlayer/{footballPlayerId}" var="contractPlayerUrl">
                        <spring:param name="footballPlayerId" value="${contractPlayer.player.id}"/>
                </spring:url>
					<b><a href="${fn:escapeXml(contractPlayerUrl)}"><c:out value="${contractPlayer.player.firstName} ${contractPlayer.player.lastName}"/></a></b>
                </td>
                <td>
                    <c:out value="${contractPlayer.salary} €"/>
                </td>
                <td>
                    <c:out value="${contractPlayer.endDate}"/>
                </td>        	
            </tr>
        </c:forEach>
        </tbody>
        </table>
        
</petclinic:layout>
