<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

		<fmt:message key="code.conlumn.name" var="Name"/>
    	<fmt:message key="code.label.email" var="Email"/>
    	<fmt:message key="code.label.dni" var="Dni"/>
    	<fmt:message key="code.label.telephone" var="Telephone"/>
    	<fmt:message key="code.title.AuthenticatedInformation" var="AuthenticatedInfo"/>
    	<fmt:message key="code.crud.editProfile" var="EditProfile"/>

<petclinic:layout pageName="authenticateds">
    <h2>Authenticateds</h2>

    <table id="authenticatedsTable" class="table table-striped">
        <thead>
        <tr>
            <th style="width: 150px;">Name</th>
            <th style="width: 200px;">Dni</th>
            <th>Email</th>
            <th style="width: 120px">Telephone</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${selections}" var="authenticated">
            <tr>
                <td>
                    <spring:url value="/authenticateds/{authenticatedId}" var="authenticatedUrl">
                        <spring:param name="authenticatedId" value="${authenticated.id}"/>
                    </spring:url>
                    <a href="${fn:escapeXml(authenticatedUrl)}"><c:out value="${authenticated.firstName} ${authenticated.lastName}"/></a>
                </td>
                <td>
                    <c:out value="${authenticated.dni}"/>
                </td>
                <td>
                    <c:out value="${authenticated.email}"/>
                </td>
                <td>
                    <c:out value="${authenticated.telephone}"/>
                </td>
                               
            </tr>
        </c:forEach>
        </tbody>
    </table>
</petclinic:layout>
