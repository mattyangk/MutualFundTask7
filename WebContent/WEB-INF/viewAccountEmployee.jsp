<%@ page import="databeans.CustomerBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />


<% CustomerBean customer=(CustomerBean)request.getAttribute("customer"); %>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Employee View Customer Account Information</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp">
	
	<% if(customer==null)
	{%>
	  <div >
		<form  method="POST" action="employeeViewCustomerAction.do">
		  
		<table>
		<tr>
			<td>Customer Name :</td> 
			<td><input type="text"  name="customerName" size="21" maxlength="120" value=""/></td>
			 <td> <input type="submit" 
				name="submit" value="Submit"></td>
	   </tr>
	    </table>
		</form>
	 
	</div>
	<%} 
	
	else { %>
	
	<table>
			<tr>
				<td>User ID: <%=customer.getCustomer_id()%></td>
				</tr>
			<tr>
				<td>User Name: <%=customer.getUsername()%></td>

			</tr>
			<tr>
				<td>First Name: <%=customer.getFirstname()%></td>
				</tr>
			<tr>
				<td>Last Name: <%=customer.getLastname()%></td>
			</tr>
			<tr>
				<td>Address :</br><%=customer.getAddr_line1()%></br> <%=customer.getAddr_line2()%>
				</td>

			</tr>
			<tr>
				<td>City:<%=customer.getCity()%></td>
				</tr>
			<tr>
				<td>State:<%=customer.getState()%></td>
				</tr>
			<tr>
				<td>Zip:<%=customer.getZip()%></td>
			</tr>

			<tr>
				<td>Cash:<%=customer.getCash()%></td>
				</tr>
			<tr>
				<td>Available Balance:<%=customer.getBalance()%></td>
			</tr>
		</table>
		<%} %>
</div>
