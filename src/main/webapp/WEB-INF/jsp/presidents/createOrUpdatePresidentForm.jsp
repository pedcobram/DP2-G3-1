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
    	<fmt:message key="code.title.President" var="President"/>
    	<fmt:message key="code.crud.update" var="Update"/>

<petclinic:layout pageName="presidents">
    <h2>
        <c:if test="${president['new']}">${New} </c:if> ${President}
    </h2>
    
    <form:form modelAttribute="president" class="form-horizontal" id="add-president-form">
      
       		<div class="form-group has-feedback">
            	<petclinic:inputField label="code.label.firstName" name="firstName"/>
            	<petclinic:inputField label="code.label.lastName" name="lastName"/>
            	<petclinic:inputField label="code.label.dni" name="dni"/>
            	<petclinic:inputField label="code.label.email" name="email"/>
            	<petclinic:inputField label="code.label.telephone" name="telephone"/>
            	<petclinic:inputField label="Username:" name="user.username"/>
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
