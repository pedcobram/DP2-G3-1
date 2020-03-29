<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="contractsCommercial">
 		<h2 style="color:black"><fmt:message key="contractsCommercial"/></h2>
 		<table id="vetsTable" class="table table-striped">
			<thead>
       			<tr>
           			<th><fmt:message key="startDate"/></th>
           			<th><fmt:message key="money"/></th>
           			<th><fmt:message key="clause"/></th>
 				</tr>
        	</thead>
        	<tbody>
        <c:forEach items="${contractsCommercial}" var="contract">
            <tr>
                <td>
                    <spring:url value="/contractsCommercial/{contractCommercialId}" var="contractCommercialURL">
                        <spring:param name="contractCommercialId" value="${contract.id}"/>
                </spring:url>
                <img width=30px height= auto hspace="20" src="${contract.publicity}"/>
                	<a href="${fn:escapeXml(contractCommercialURL)}"><b><c:out value="${contract.id}"/></b></a>
                </td>
                <td>
                    <c:out value="${contract.money}"/> €
                </td>
                <td>
                    <c:out value="${contract.clause}"/> €
                </td> 
            </tr>
        </c:forEach>
        </tbody>
        </table>
</petclinic:layout>
