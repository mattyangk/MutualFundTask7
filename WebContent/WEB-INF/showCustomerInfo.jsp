<%@ page import="databeans.CustomerBean"%>
<%@ page import="databeans.PositionBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<jsp:include page="header.jsp" />




<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Employee View Customer Account Information</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />


	
			<div>
				<form method="POST" action="employeeViewCustomerAction.do">

					<table>
						<tr>
							<td>Customer Name :</td>
							<td><input type="text" name="customerName" size="21"
								maxlength="120" value="" /></td>
							<td><input type="submit" name="submit" value="Submit"></td>
						</tr>
					</table>
				</form>

			</div>
			

			<table>
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
					<td>Cash:${customer.cash}</td>
				</tr>
				<tr>
					<td>Available Balance:${customer.balance}</td>
				</tr>
				<tr>
				     <td>Funds</td>
				</tr>
			    
				<c:forEach  items="${fundInfo}" var="fund">
				<tr>
				    <td>${fund.fund_name}</td>
				    <td>${fund.fund_symbol}</td>
				    <td>${fund.shares}</td>
				</tr>
				</c:forEach>
				
			
			</table>
		

</div>
