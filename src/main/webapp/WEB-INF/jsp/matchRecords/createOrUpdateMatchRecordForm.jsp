<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html; charset=UTF-8" %> <!-- Para  tildes, ñ y caracteres especiales como el € %-->

<petclinic:layout pageName="matchRecord">

<jsp:body>

  <h2 class="th-center"><fmt:message key="code.title.createorupdatemaatchrecordform"/></h2>
    		
    <form:form modelAttribute="matchRecord" class="form-horizontal" id="add-footballPlayer-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="code.label.createorupdatemaatchrecordform.title" name="title" />
            <petclinic:inputField label="code.label.createorupdatemaatchrecordform.season" name="season_start" />
            <petclinic:inputField label="code.label.createorupdatemaatchrecordform.result" name="result"/>
            <div class="control-group">
                  <c:if test="${!matchRecord['new']}">
                  <petclinic:selectField label="code.label.createorupdatemaatchrecordform.winner" name="winner" names="${winner}" size="3" />
                  </c:if>   
                    <petclinic:selectField label="code.label.createorupdatemaatchrecordform.matchstatus" name="status" names="${matchStatus}" size="2" />
                </div>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${!matchRecord['new']}">
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatemaatchrecordform.update"/></button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit"><fmt:message key="code.button.createorupdatemaatchrecordform.create"/></button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
   
    </jsp:body>
    
    
</petclinic:layout>