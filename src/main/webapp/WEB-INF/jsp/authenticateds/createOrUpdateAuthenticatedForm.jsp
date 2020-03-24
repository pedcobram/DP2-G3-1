<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

    	<fmt:message key="code.title.Authenticated" var="Authenticated"/>
    	<fmt:message key="code.crud.update" var="Update"/>
    	<fmt:message key="code.crud.add" var="Add"/>
    	<fmt:message key="code.crud.new" var="New"/>

<petclinic:layout pageName="authenticateds">
    <h2>
        <c:if test="${authenticated['new']}">${New} </c:if> ${Authenticated}
    </h2>
    <form:form modelAttribute="authenticated" class="form-horizontal" id="add-authenticated-form">
        <div class="form-group has-feedback">
        
            <petclinic:inputField label="code.label.firstName" name="firstName"/>
            <petclinic:inputField label="code.label.lastName" name="lastName"/>
            <petclinic:inputField label="code.label.dni" name="dni"/>
            <petclinic:inputField label="code.label.email" name="email"/>
            <petclinic:inputField label="code.label.telephone" name="telephone"/>
            <petclinic:inputField label="Username" name="user.username" readonly="${!authenticated['new']}"/>
            <petclinic:inputField label="Password" name="user.password" readonly="${!authenticated['new']}"/>
          
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${authenticated['new']}">
                        <button class="btn btn-default" type="submit">${Add}</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">${Update}</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>

