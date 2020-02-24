<%@ page session="false" trimDirectiveWhitespaces="true" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="petclinic" tagdir="/WEB-INF/tags" %>

<petclinic:layout pageName="footballClubs">

	<jsp:attribute name="customScript">
        <script>
            $(function () {
                $("#foundationDate").datepicker({dateFormat: 'yy/mm/dd'});
            });
        </script>
    </jsp:attribute>
<jsp:body>
    <h2>
        <c:if test="${footballClub['new']}">New </c:if> FootballClub
    </h2>
    <form:form modelAttribute="footballClub" class="form-horizontal" id="add-footballClub-form">
        <div class="form-group has-feedback">
            <petclinic:inputField label="Name" name="name"/>
            <petclinic:inputField label="Logo" name="crest"/>
            <petclinic:inputField label="City" name="city"/>
            <petclinic:inputField label="Stadium" name="stadium"/>
            <petclinic:inputField label="Fans" name="fans"/>
            <petclinic:inputField label="Coach" name="coach"/>
            <petclinic:inputField label="Money" name="money"/>
            <petclinic:inputField label="Foundation Date" name="foundationDate"/>
        </div>
        <div class="form-group">
            <div class="col-sm-offset-2 col-sm-10">
                <c:choose>
                    <c:when test="${footballClub['new']}">
                        <button class="btn btn-default" type="submit">Add FootballClub</button>
                    </c:when>
                    <c:otherwise>
                        <button class="btn btn-default" type="submit">Update FootballClub</button>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
        
    </form:form>
    </jsp:body>
</petclinic:layout>