<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

		<fmt:message key="code.crud.new" var="New"/>
		<fmt:message key="code.label.firstName" var="firstName"/>
		<fmt:message key="code.label.lastName" var="lastName"/>
    	<fmt:message key="code.label.email" var="Email"/>
    	<fmt:message key="code.label.dni" var="Dni"/>
    	<fmt:message key="code.label.telephone" var="Telephone"/>
    	<fmt:message key="code.title.President" var="President"/>
    	<fmt:message key="code.crud.update" var="Update"/>

<petclinic:layout pageName="presidents">
    <h2>
        <c:if test="${president['new']}">${New} </c:if> ${President}
    </h2>
    
    <form:form modelAttribute="president" class="form-horizontal" id="add-president-form">
      
       		<div class="form-group has-feedback">
            	<petclinic:inputField label="${firstName}" name="firstName"/>
            	<petclinic:inputField label="${lastName}" name="lastName"/>
            	<petclinic:inputField label="${Dni}" name="dni"/>
            	<petclinic:inputField label="${Email}" name="email"/>
            	<petclinic:inputField label="${Telephone}" name="telephone"/>
            	<petclinic:inputField label="Username" name="user.username" readonly="true"/>
            	<petclinic:inputField label="Password" name="user.password" readonly="true"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <button class="btn btn-default" type="submit">${Update}</button>                
            </div>
        </div>
    </form:form>
</petclinic:layout>
