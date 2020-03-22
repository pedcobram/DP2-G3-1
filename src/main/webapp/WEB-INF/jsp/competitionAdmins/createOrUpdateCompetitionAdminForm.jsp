<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="competitionAdmins">
    <h2>
        <c:if test="${competitionAdmin['new']}"><fmt:message key="code.title.createorupdatecompetitionadminform.new"/> </c:if>
        <c:if test="${!competitionAdmin['new']}"><fmt:message key="code.title.createorupdatecompetitionadminform.notnew"/> </c:if>
    </h2>

    
    <form:form modelAttribute="competitionAdmin" class="form-horizontal" id="add-competition-admin-form">
      
       		<div class="form-group has-feedback">
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.firstname" name="firstName"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.lastname" name="lastName"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.dni" name="dni"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.email" name="email"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.telephone" name="telephone"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.username" name="user.username"/>
            <petclinic:inputField label="code.label.createorupdatecompetitionadminform.password" name="user.password"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${competitionAdmin['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatecompetitionadminform.submit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatecompetitionadminform.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
