<%--
- debug-bindings.tag
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
	import="java.util.Enumeration, java.util.Map, java.util.HashMap, org.springframework.validation.BindingResult"
%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<%
	Map<String, Object> bindings;
	Enumeration<?> keys;
	Boolean showSystem;
	Integer scope;
	
	bindings = new HashMap<String, Object>();

	scope = (Integer)jspContext.getAttribute("scope");
	if (scope == null)
		scope = 2;
	else 
		scope = scope + 1;	
	
	keys = jspContext.getAttributeNamesInScope(scope);
	while (keys.hasMoreElements())
	{
		String name;
	    Object value;
	    
	    name = (String) keys.nextElement();	    
	    value = jspContext.getAttribute(name, scope);
	    if (name.startsWith(BindingResult.MODEL_KEY_PREFIX))
	    	bindings.put(name.substring(BindingResult.MODEL_KEY_PREFIX.length()), value);
	}
    jspContext.setAttribute("bindings", bindings);
%>

<div class="alert alert-info" style="font-family: monospace;">
	<h2>Bindings</h2>
	<dl>
		<jstl:forEach var="entry" items="${bindings.entrySet()}">
			<dt>
				<jstl:out value="${entry.key}"/> 
			</dt>
			<dd>
				<ul>
					<jstl:forEach var="error" items="${entry.value.getGlobalErrors()}">
						<li>
							<jstl:out value="*"/> = 
							"<jstl:out value="${error.getRejectedValue()}"/>" : 
							<jstl:out value="${error.getDefaultMessage()}"/>
						</li>					
					</jstl:forEach>
					<jstl:forEach var="error" items="${entry.value.getFieldErrors()}">
						<li>
							<jstl:out value="${error.getField()}"/> =
							"<jstl:out value="${error.getRejectedValue()}"/>" : 
							<jstl:out value="${error.getDefaultMessage()}"/>
						</li>					
					</jstl:forEach>
				</ul>
			</dd>						
		</jstl:forEach>		
	</dl>
</div>
	