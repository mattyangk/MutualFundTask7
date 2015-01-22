<%@ page import="databeans.CustomerBean" %>
<%@ page import="databeans.EmployeeBean" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	CustomerBean customer = (CustomerBean) request.getSession()
			.getAttribute("customer");
	EmployeeBean employee = (EmployeeBean) request.getSession()
			.getAttribute("employee");
%>


<jsp:include page="header.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	
	<%
		if (customer != null) {
	%>
	<h1 class="page-header">
		Welcome
		<%=customer.getUsername()%></h1>

	<%
		} else {
	%>
	<h1 class="page-header">
		Welcome
		<%=employee.getUsername()%></h1>
	<%
		}
	%>

	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
</div>
