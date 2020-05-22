<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="matchDateChangeRequest">
    
    <h2>
    	 <c:choose>
			<c:when test="${matchDateChangeRequest['new']}">
            	<fmt:message key="code.title.createorupdatematchdatechangerequestform.new"/>
           	</c:when>
            <c:otherwise>
            	<fmt:message key="code.title.createorupdatematchdatechangerequestform"/>
        	</c:otherwise>
		</c:choose>
    </h2>
    
    <form:form modelAttribute="matchDateChangeRequest" class="form-horizontal" id="add-referee-form">
    	
       		<div class="form-group has-feedback">
           		<petclinic:inputField label="code.label.createorupdatematchdatechangerequestform.title" name="title"/>
           		<petclinic:inputField label="code.label.createorupdatematchdatechangerequestform.newDate" name="new_date"/>
        		<petclinic:inputField label="code.label.createorupdatematchdatechangerequestform.reason" name="reason"/>
		<c:if test="true">           		
           		<petclinic:inputField label="code.label.createorupdatematchdatechangerequestform.status" name="status" readonly="true"/>
           		<petclinic:inputField label="code.label.createorupdatematchdatechangerequestform.creator" name="request_creator" readonly="true"/>
		</c:if>         		
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${matchDateChangeRequest['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatematchdatechangerequestform.submit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatematchdatechangerequestform.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
