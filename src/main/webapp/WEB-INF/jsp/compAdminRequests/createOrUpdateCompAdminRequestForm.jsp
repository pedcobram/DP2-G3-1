<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="compAdminRequests">
    <h2>
        <c:if test="${compAdminRequests['new']}">New </c:if> Competition Admin Request
    </h2>
    
    <form:form modelAttribute="compAdminRequest" class="form-horizontal" id="add-competition-admin-request-form">
      
       		<div class="form-group has-feedback">
            <petclinic:inputField label="Title" name="title"/>
            <petclinic:inputField label="Description" name="description"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${compAdminRequests['new']}">
                        <button class="btn btn-default" type="submit">Request to be a Competition Admin</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update Competition Admin Request</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
