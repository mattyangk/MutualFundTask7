<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">All Customers Account's Information</h1>
	<jsp:include page="error.jsp" />
	<table class="table">
		<tr>
			<th>User ID</th>
			<th>User Name</th>
			<th>First Name</th>
			<th>Last Name</th>
			<th>Cash</th>
			<th>Available Balance</th>
		</tr>
		<tr>
			<c:forEach var="customer" items="${customersList}">
				<td>${customer.customer_id}</td>
				<td>${customer.username}</td>
				<td>${customer.firstname}</td>
				<td>${customer.lastname}</td>
				<td>${customer.cash}</td>
				<td>${customer.balance}</td>
			</c:forEach>	
		</tr>
		</table>
</div>