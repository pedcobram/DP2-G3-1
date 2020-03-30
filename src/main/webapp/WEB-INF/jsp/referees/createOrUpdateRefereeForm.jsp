<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="referees">
    <h2>
    
        <c:if test="${referee['new']}"><fmt:message key="code.title.createorupdaterefereeform.new"/></c:if> <c:out value=" "/> <fmt:message key="code.title.createorupdaterefereeform"/>
    </h2>
    
    <form:form modelAttribute="referee" class="form-horizontal" id="add-referee-form">
      
       		<div class="form-group has-feedback">
            <petclinic:inputField label="code.label.createorupdaterefereeform.firstname" name="firstName"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.latname" name="lastName"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.dni" name="dni"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.email" name="email"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.telephone" name="telephone"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.username" name="user.username" readonly="true"/>
            <petclinic:inputField label="code.label.createorupdaterefereeform.password" name="user.password"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${referee['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdaterefereeform.submit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdaterefereeform.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
