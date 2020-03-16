<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->


	<fmt:message key="code.title.friendlyMatch" var="friendlyMatch"/> 


<petclinic:layout pageName="matchRequests">
   		
   	<jsp:body>
   	<h2>
        <c:if test="${matchRequest['new']}"><fmt:message key="newMatchRequestForm"/> </c:if>
        <c:if test="${!matchRequest['new']}"><fmt:message key="notNewMatchRequestForm"/> </c:if>
    </h2>
    
    <form:form modelAttribute="matchRequest" class="form-horizontal" id="add-match-request-form">
      
       		<div class="form-group has-feedback">
       		<div class="form-group">
				<label class="col-sm-2 control-label">Title:</label>
				<div class="col-sm-10">
					<div class="form-control" style="background-color: #f1f1f1"> 
						${titleMatch} ${friendlyMatch} 
						<span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
					</div>
				</div>	
			</div>
            <petclinic:inputField label="Match Date" name="matchDate"/>
            <div class="control-group">
            	<petclinic:selectField label="Stadium" name="stadium" names="${stadiums}" size="2"/>
            </div>
            <petclinic:inputField label="Football Club 1" name="footballClub1.name" readonly="true"/>
            <petclinic:inputField label="Football Club 2" name="footballClub2.name" readonly="true"/>
        	</div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${matchRequest['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="submitCompAdminRequestForm"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="updateCompAdminRequestForm"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </form:form>
    </jsp:body>
</petclinic:layout>
