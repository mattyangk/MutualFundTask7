<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />

<%
	CustomerBean customer = (CustomerBean) request.getSession()
			.getAttribute("customer");
%>


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Account Information</h1>
	<jsp:include page="error.jsp" />
	<table class="table">

			<tr>
				<td>User ID: <%=customer.getCustomer_id()%></td>
				<td>User Name: <%=customer.getUsername()%></td>

			</tr>
			<tr>
				<td>First Name: <%=customer.getFirstname()%></td>
				<td>Last Name: <%=customer.getLastname()%></td>
			</tr>
			<tr>
				<td>Address Line1:<%=customer.getAddr_line1()%></td>

			</tr>
			<tr>
				<td>Address Line2:<%=customer.getAddr_line2()%></td>
			<tr>
				<td>City:<%=customer.getCity()%></td>
				<td>State:<%=customer.getState()%></td>
				<td>Zip:<%=customer.getZip()%></td>
			</tr>

			<tr>
				<td>Cash:<%=customer.getCash()%></td>
				<td>Balance:<%=customer.getBalance()%></td>
			</tr>
		</table>
</div>