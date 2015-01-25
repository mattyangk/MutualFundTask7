<%@ page import="databeans.CustomerBean"%>
<%@ page import="databeans.PositionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Customer Account Information</h1>
     <jsp:include page="error.jsp" />
      <jsp:include page="message.jsp" />

     <table class="table">

		<tr>

			<td>User ID: ${customer.customer_id}</td>

		</tr>

		<tr>

			<td>User Name:${customer.username }</td>


		</tr>

		<tr>

			<td>First Name:${customer.firstname }</td>

		</tr>

		<tr>

			<td>Last Name: ${customer.lastname }</td>

		</tr>

		<tr>

			<td>Address 1: ${customer.addr_line1}</td>

		</tr>

		<tr>

			<td>Address 2: ${customer.addr_line2 }</td>

		</tr>

		<tr>

			<td>City:${customer.city }</td>

		</tr>

		<tr>

			<td>State:${customer.state}</td>

		</tr>

		<tr>

			<td>Zip:${customer.zip }</td>

		</tr>


		<tr>

			<td>Cash:<fmt:formatNumber value="${customer.cash}"
					type="currency" /></td>

		</tr>

		<tr>

			<td>Available Balance:<fmt:formatNumber value="${customer.balance}"
					type="currency" /></td>

		</tr>

	</table>

	<table class="table">

		<thead>

			<tr>
				<td>Fund List</td>
		</thead>
		 <c:choose>
		<c:when test="${empty fundInfo}">
		<tr><td>You don't have any fund now.</td></tr>
		</c:when>
	    <c:otherwise>

		<tbody>

			<tr>

				<th>Fund Name</th>

				<th>Ticker</th>

				<th>Shares</th>
			<tr>


				<c:forEach items="${fundInfo}" var="fund">

					<tr>

						<td>${fund.fund_name}</td>

						<td>${fund.fund_symbol}</td>

						<td><fmt:formatNumber value="${fund.shares}" type="number"
								maxFractionDigits="3" minFractionDigits="3"/></td>

					</tr>

				</c:forEach>
			<tr>

				<td>${message}</td>

			</tr>

		</tbody>
		</c:otherwise>
		</c:choose>

	</table>






</div>

