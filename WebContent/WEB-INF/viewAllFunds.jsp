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
	<h1 class="page-header">All Funds</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<table data-height="600" data-sort-name="name" data-sort-order="desc"
		data-search="true" data-pagination="true">

		<thead>
			<tr>
				<th data-align="center" data-sortable="true">Fund Name</th>
				<th data-align="center" data-sortable="true">Fund Symbol</th>
				<th data-align="center" data-sortable="true">Latest Price</th>
			</tr>

		</thead>


		<tbody>

			<c:forEach var="fund" items="${fundPriceDetails}">
				<tr>
					<td><a href="researchFund.do?fundname=${fund.name}">${fund.name}</a></td>
					<td>${fund.symbol}</td>
					<td><fmt:formatNumber value="${fund.price}" type="number"
								maxFractionDigits="2" minFractionDigits="2" /></td>
				</tr>
			</c:forEach>

		</tbody>
	</table>
</div>