<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="coachTransferRequest">
    
    <h2>
    	 <c:choose>
			<c:when test="${coachTransferRequest['new']}">
            	<fmt:message key="code.title.createorupdatecoachtransferrequestform.new"/>
           	</c:when>
            <c:otherwise>
            	<fmt:message key="code.title.createorupdatecoachtransferrequestform"/>
        	</c:otherwise>
		</c:choose>
    </h2>
    
    <table class="table table-striped">
   	 	<tr>
            <th><fmt:message key="code.label.createorupdatecoachtransferrequestform.requestedCoach"/></th>
            <td><b><c:out value="${coachTransferRequest.requestedCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.requestedCoach.lastName}"/></b></td>
        </tr>
        <tr>
        	<th><fmt:message key="code.label.createorupdatecoachtransferrequestform.requestedClubName"/></th>
            <td><b><c:out value="${coachTransferRequest.requestedCoach.club.name}"/></b></td>
        </tr>
        <tr>
        	<th><fmt:message key="code.label.createorupdatecoachtransferrequestform.myCoach"/></th>
            <td><b><c:out value="${coachTransferRequest.myCoach.firstName}"/> <c:out value=" "/> <c:out value="${coachTransferRequest.myCoach.lastName}"/></b></td>
        </tr>
        <tr>
        	<th><fmt:message key="code.label.createorupdatecoachtransferrequestform.myClubName"/></th>
            <td><b><c:out value="${coachTransferRequest.myCoach.club.name}"/></b></td>
        </tr>
    </table>
    
    <form:form modelAttribute="coachTransferRequest" class="form-horizontal" id="add-referee-form">
    	
       		<div class="form-group has-feedback">
           		<petclinic:inputField label="code.label.createorupdatecoachtransferrequestform.coachOffer" name="offer"/>
		<c:if test="false">           		
           		<petclinic:inputField label="code.label.createorupdatecoachtransferrequestform.status" name="status" readonly="true"/>
        		<petclinic:inputField label="code.label.createorupdatecoachtransferrequestform.requestedCoach" name="requestedCoach" readonly="true"/>
         		<petclinic:inputField label="code.label.createorupdatecoachtransferrequestform.myCoach" name="myCoach" readonly="true"/>
		</c:if>         		
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${coachTransferRequest['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatecoachtransferrequestform.submit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatecoachtransferrequestform.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
