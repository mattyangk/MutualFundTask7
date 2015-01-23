<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Account Information</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<table class="table">
	        
			<tr>
				<td>User ID: ${customer.customer_id}</td>
				</tr>
			<tr>
				<td>User Name: ${customer.username}</td>

			</tr>
			<tr>
				<td>First Name:${customer.firstname}</td>
				</tr>
			<tr>
				<td>Last Name: ${customer.lastname}</td>
			</tr>
			<tr>
				<td>Address :<br>${customer.addr_line1}<br> ${customer.addr_line2 }
				</td>

			</tr>
			<tr>
				<td>City:${customer.city}</td>
				</tr>
			<tr>
				<td>State:${customer.state}</td>
				</tr>
			<tr>
				<td>Zip:${customer.zip}</td>
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
		
</div>