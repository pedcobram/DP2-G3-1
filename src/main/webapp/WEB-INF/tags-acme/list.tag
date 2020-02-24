<%--
- list.tag
-
- Copyright (c) 2019 Rafael Corchuelo.
-
- In keeping with the traditional purpose of furthering education and research, it is
- the policy of the copyright owner to permit non-commercial use and redistribution of
- this software. It has been tested carefully, but it is not guaranteed for any particular
- purposes.  The copyright owner does not offer any warranties or representations, nor do
- they accept any liabilities with respect to them.
--%>

<%@tag language="java" 
	import="java.util.Collection,java.util.ArrayList,java.util.Map,acme.framework.helpers.JspHelper"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="readonly" required="false" type="java.lang.Boolean"%>

<jstl:if test="${readonly == null}">
	<jstl:set var="readonly" value="false"/>
</jstl:if>

<%
	Collection<Map<String, Object>> dataTableColumns;
	String mapName;

	dataTableColumns = new ArrayList<Map<String, Object>>();
	request.setAttribute("__data_table_columns", dataTableColumns);
%>

<table id="list" class="table table-striped table-condensed table-hover nowrap w-100">
	<jsp:doBody var="body"/>
	<thead>
		<tr>
			<th><%-- Placeholder for details button --%></th>
			<jstl:set var="aoColumns" value="'aoColumns': [ {'bSortable': false}"/>			
			<jstl:forEach var="column" items="${__data_table_columns}">
				<th style="width: ${column.width}">
					<jstl:if test="${column.code != null}">
						<spring:message code="${column.code}"/>
					</jstl:if>
					<jstl:set var="aoColumns" value="${aoColumns}, {'bSortable': ${column.sortable}}"/>
				</th>
			</jstl:forEach>
			<jstl:set var="aoColumns" value="${aoColumns} ]"/>
		</tr>
	</thead>
	<tbody>
		<jstl:if test="${model$size >= 1}">
			<jstl:forEach var="index" begin="${0}" end="${model$size - 1}">
				<jstl:choose>
					<jstl:when test="${model$size == 1}">
						<jstl:set var="index_id" value="id"/>						
					</jstl:when>
					<jstl:otherwise>
						<jstl:set var="index_id" value="id[${index}]"/>
					</jstl:otherwise>
				</jstl:choose>
				
				<tr data-item-id="${requestScope[index_id]}">
					<td><%-- Placeholder for details button --%></td>
					<jstl:forEach var="column" items="${__data_table_columns}">
						<jstl:set var="path" value="${column.path}"/> 
						<jstl:set var="format" value="${column.format}"/>
						<jstl:choose>
							<jstl:when test="${model$size == 1}">
								<jstl:set var="index_path" value="${path}"/>				
							</jstl:when>
							<jstl:otherwise>
								<jstl:set var="index_path" value="${path}[${index}]"/>
							</jstl:otherwise>
						</jstl:choose>
						<jstl:set var="dataSort" value="${JspHelper.computeDataSort(requestScope[index_path])}"/>	
						<jstl:set var="dataText" value="${JspHelper.computeDataText(requestScope[index_path], format)}"/>
					 	<td ${dataSort}>
					 		<acme:print value="${dataText}"/>							
						</td>
					</jstl:forEach>
				</tr>
			</jstl:forEach>
		</jstl:if>
	</tbody>
</table>

<spring:message var="i18nFileUri" code="datatables.language"/>
<jstl:set var="language" value="'language': { 'url': getAbsoluteUrl('${i18nFileUri}') }"/>

<script type="text/javascript">
	$(document).ready(function() {
		var table;
		
		table = $("#list").dataTable({
			  "lengthMenu": [5, 10, 25, 50],
			  "pageLength": 5,
			  "pagingType": "numbers",
 			   "stateSave": true,
			  "responsive": { details: { type: "column" } },
			  "columnDefs": [ { className: "control", orderable: false, targets: 0 } ],
			         "dom": "<'row'<'col'f><'col'i>>" +
				            "<'row'<'col'tr>>" +
				            "<'row'<'col'l><'col'p>>",
				   "order": [[ 0, "asc" ]],
	        <jstl:out value="${language}" escapeXml="false"/>,
			<jstl:out value="${aoColumns}" escapeXml="false"/>,
	    });
		
		<jstl:if test="${!readonly}">
		    $("#list tbody").on("click", "td", function () {
		    	var id, row, column, singleColumn;
		    	
		    	row = $(this).parent().parent().children().index($(this).parent());
		    	column = $(this).parent().children().index($(this));
		    	singleColumn = $(this).parent().children().length == 1;
		    			    	
		    	if (singleColumn || column >= 1) {
			    	id = $(this).parent().attr("data-item-id")
			    	if (typeof(id) != 'undefined') {	    		
			    		pushReturnUrl("<%=JspHelper.getRequestUrl(request)%>");
			    		redirect("show?id={0}".format(id));
			    	}
		    	}
	    	});
		</jstl:if>
	});	
</script>

