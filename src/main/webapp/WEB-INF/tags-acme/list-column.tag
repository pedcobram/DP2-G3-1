<%--
- list-column.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" body-content="empty"
	   import="java.util.Map, java.util.HashMap, acme.framework.helpers.JspHelper"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="path" required="true" type="java.lang.String"%>
<%@attribute name="code" required="true" type="java.lang.String"%>
<%@attribute name="format" required="false" type="java.lang.String"%>
<%@attribute name="sortable" required="false" type="java.lang.Boolean"%>
<%@attribute name="width" required="false" type="java.lang.String"%>

<jstl:if test="${format == null}">
	<jstl:set var="format" value="{0}"/>
</jstl:if>

<jstl:if test="${sortable == null}">
	<jstl:set var="sortable" value="${true}"/>
</jstl:if>

<jstl:if test="${width == null}">
	<jstl:set var="width" value="inherited"/>
</jstl:if>

<%	
	Map<String, Object> column;
	Boolean sortable;

	column = new HashMap<String, Object>(); 	
	column.put("code", jspContext.getAttribute("code"));
	column.put("path", jspContext.getAttribute("path"));	
	column.put("format", jspContext.getAttribute("format"));
	column.put("sortable", jspContext.getAttribute("sortable"));
	column.put("width", jspContext.getAttribute("width"));	
	
	JspHelper.updateDatatableColumns(request, column);	
%>


