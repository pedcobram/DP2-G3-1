<%--
- debug-model.tag
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
	import="java.util.Enumeration, java.util.SortedMap,	java.util.TreeMap, java.lang.StringBuffer,
		acme.framework.helpers.PrinterHelper, acme.framework.utilities.ModelKeyComparator"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%@attribute name="system" required="false" type="java.lang.Boolean"%>
<%@attribute name="scope" required="false" type="java.lang.Integer"%>

<jstl:if test="${system == null}">
	<jstl:set var="system" value="false"/>
</jstl:if>

<jstl:if test="${scope == null}">
	<%-- PAGE_SCOPE = 1, REQUEST_SCOPE = 2, SESSION_SCOPE = 3, APPLICATION_SCOPE = 4 --%>
	<jstl:set var="scope" value="2"/>
</jstl:if>

<%
	ModelKeyComparator comparator;
	SortedMap<String, Object[]> model, internalModel, singleModel, indexedModel;
	Enumeration<?> keys;
	Boolean showSystem;
	Integer scope;
	Object[] pair;
	
	comparator = new ModelKeyComparator();
	internalModel = new TreeMap<String, Object[]>(comparator);
	singleModel = new TreeMap<String, Object[]>(comparator);
	indexedModel = new TreeMap<String, Object[]>(comparator);	
	
	showSystem = (Boolean)jspContext.getAttribute("system");
	scope = Integer.valueOf((String)jspContext.getAttribute("scope"));			
	keys = jspContext.getAttributeNamesInScope(scope);	 
	while (keys.hasMoreElements())
	{
		String name;
	    Object value;
	    StringBuffer buffer;
	    
	    name = (String) keys.nextElement();	    
	    value = jspContext.getAttribute(name, scope);
	    buffer = new StringBuffer();
	    PrinterHelper.printValue(buffer, value, false);
	    if (name.contains("["))
	    	model = indexedModel;
	    else if (name.contains("$"))
	    	model = internalModel;
	    else 
	    	model = singleModel;	    
	    pair = new Object[]{ buffer.toString(), value.getClass().getName() };
	    if (!showSystem && !name.matches("^.*(\\.|__|[Ss][Pp][Rr][Ii][Nn][Gg]).*$"))
	    	model.put(name, pair);
	    if (showSystem && name.matches("^.*(\\.|__|[Ss][Pp][Rr][Ii][Nn][Gg]).*$"))
	    	model.put(name, pair);
	}
	jspContext.setAttribute("internalModel", internalModel);
    jspContext.setAttribute("singleModel", singleModel);
    jspContext.setAttribute("indexedModel", indexedModel);    
%>

<div class="alert alert-info" style="font-family: monospace;">
	<h2>Model variables (${system ? "System scope" : "User scope"})</h2>
	<dl>
		<jstl:forEach var="entry" items="${internalModel.entrySet()}">
			<dt><jstl:out value="${entry.key}: ${entry.value[1]}"/></dt>
			<dd style="word-wrap: break-word; white-space: pre-wrap; margin-left: 2em;"><jstl:out value="${entry.value[0]}"/></dd>
		</jstl:forEach>
		<jstl:forEach var="entry" items="${singleModel.entrySet()}">
			<dt><jstl:out value="${entry.key}: ${entry.value[1]}"/></dt>
			<dd style="word-wrap: break-word; white-space: pre-wrap; margin-left: 2em;"><jstl:out value="${entry.value[0]}"/></dd>
		</jstl:forEach>
		<jstl:forEach var="entry" items="${indexedModel.entrySet()}">
			<dt><jstl:out value="${entry.key}: ${entry.value[1]}"/></dt>
			<dd style="word-wrap: break-word; white-space: pre-wrap; margin-left: 2em;"><jstl:out value="${entry.value[0]}"/></dd>
		</jstl:forEach>
	</dl>
</div>

