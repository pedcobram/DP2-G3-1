<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="playerTransferRequest">
    
    <h2>
    	 <c:choose>
			<c:when test="${playerTransferRequest['new']}">
            	<fmt:message key="code.title.createorupdateplayertransferrequestform.new"/>
           	</c:when>
            <c:otherwise>
            	<fmt:message key="code.title.createorupdateplayertransferrequestform"/>
        	</c:otherwise>
		</c:choose>
    </h2>
    
    <table class="table table-striped">
   	 	<tr>
            <th><fmt:message key="code.label.createorupdateplayertransferrequestform.playerValue"/></th>
            <td><b><c:out value="${playerTransferRequest.playerValue}€"/></b></td>
        </tr> 
       <!--  <tr>
            <th><fmt:message key="code.label.createorupdateplayertransferrequestform.fundsRemaining"/></th>
            <td><b><c:out value="${fundsRemaining}€"/></b></td>
        </tr>
       -->
        <tr>
            <th><fmt:message key="code.label.createorupdateplayertransferrequestform.status"/></th>
            <td><b><c:out value="${playerTransferRequest.status}"/></b></td>
        </tr>
        <tr>
            <th><fmt:message key="code.label.createorupdateplayertransferrequestform.footballPlayerName"/></th>
            <td><b><c:out value="${playerTransferRequest.footballPlayer.firstName} ${playerTransferRequest.footballPlayer.lastName}"/></b></td>
        </tr>
   	 	<tr>
            <th><fmt:message key="code.label.createorupdateplayertransferrequestform.president"/></th>
            <td><b><c:out value="${playerTransferRequest.president.firstName} ${playerTransferRequest.president.lastName}"/></b></td>
        </tr>
    </table>
    
    <form:form modelAttribute="playerTransferRequest" class="form-horizontal" id="add-referee-form">
    	
       		<div class="form-group has-feedback">
           		<petclinic:inputField label="code.label.createorupdateplayertransferrequestform.playerValue" name="playerValue"/>
		<c:if test="false">           		
           		<petclinic:inputField label="code.label.createorupdateplayertransferrequestform.status" name="status" readonly="true"/>
        		<petclinic:inputField label="code.label.createorupdateplayertransferrequestform.footballPlayerName" name="footballPlayer" readonly="true"/>
         		<petclinic:inputField label="code.label.createorupdateplayertransferrequestform.president" name="president.firstName" readonly="true"/>
		</c:if>         		
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${playerTransferRequest['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdateplayertransferrequestform.submit"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdateplayertransferrequestform.update"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
</petclinic:layout>
