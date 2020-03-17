<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="fanVip">
    <h2><fmt:message key="wantVip"/></h2>
    <p><fmt:message key="footballClub"/>: ${fan.club.name}   </p>
    <p><fmt:message key="vip"/></p>
    <br/>
    
     <form:form modelAttribute="fan" class="form-horizontal" id="add-fan-form">
   
        <div class="form-group has-feedback">
         	<input type="hidden" name="id" value="${fan.id}"/>
            <petclinic:inputField label="creditCard" name="creditCard.creditCardNumber"/>
            <petclinic:inputField label="expirationDate" name="creditCard.expirationDate"/>
            <petclinic:inputField label="cvv" name="creditCard.cvv"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
            	 <c:choose>
                        <c:when test="${isNew}">
                            <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-ok"></span><fmt:message key="yesVip"/></button>
            		<spring:url value="/footballClub/${fan.club.id}/createFanNoVip" var="FanUrl"></spring:url>
    	<a href="${fn:escapeXml(FanUrl)}" class="btn btn-default">
    				<span class="glyphicon glyphicon-remove"></span> 
    								 <fmt:message key="noVip"/></a>
                        </c:when>
                        <c:otherwise>
                            <button class="btn btn-default" type="submit"><span class="glyphicon glyphicon-ok"></span><fmt:message key="yesVip"/></button>
    						<a href="/" class="btn btn-default"><span class="glyphicon glyphicon-remove"></span><fmt:message key="noVip"/></a>

                        </c:otherwise>
                    </c:choose>
            
            
            
            
            
            
                <c:if test="${isNew}">
                
                    
                    
                </c:if>
            </div>
        </div>
    </form:form> 
     
                   
</petclinic:layout>
