<%@ page import="databeans.CustomerBean"%>
<%@ page import="java.util.ArrayList;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Customer Information</h1>
	<jsp:include page="error.jsp" />
	<%
		ArrayList<CustomerBean> customerlist = (ArrayList<CustomerBean>) request
				.getAttribute("customerlist");
		if (customerlist != null) {
			for (CustomerBean customer : customerlist) {
	%>
	<h3>
		<a href=viewAccountCustomer.do?customer.getUserId()><%=customer.getUsername()%></a>
	</h3>
	<%
			}
		}
	%>
</div>