<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />

<script src="js/bootstrap-table.js"></script>
<link rel="stylesheet" href="css/bootstrap-table.css">

<script>
	$(document).ready(function() {
		$('table').bootstrapTable({

		});
	});
</script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">All Customers Account's Information</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<table data-height="600" data-sort-name="name" data-sort-order="desc"
		data-search="true" data-pagination="true">

		<thead>
			<tr>
				<th data-align="center" data-sortable="true">User Name</th>
				<th data-align="center" data-sortable="true">Name</th>
			</tr>

		</thead>


		<tbody>

			<c:forEach var="customer" items="${customersList}">
				<tr>
					<td><a href="viewOneCustomerTransactionHistory.do?customername=${customer.username}">${customer.username}</a></td>
					<td>${customer.firstname} ${customer.lastname}</td>
					
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>