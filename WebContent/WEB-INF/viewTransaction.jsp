<%@ page import="databeans.TransactionBean"%>
<%@ page import="java.util.ArrayList;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Transaction History</h1>
	<table>
		<tr>
			<td>Transaction ID</td>
			<td>Fund ID</td>
			<td>Transaction Date</td>
			<td>Execute Date</td>
			<td>Shares</td>
			<td>Transaction Type</td>
			<td>Amount</td>
			<td>Completion</td>
			<td>Success</td>
		</tr>

		<%
			ArrayList<TransactionBean> transactions = (ArrayList<TransactionBean>) request
					.getAttribute("transactions");
			if (transactions != null) {
				for (TransactionBean transaction : transactions) {
		%>
		<tr>
			<td><%=transaction.getTransaction_id()%></td>
			<td><%=transaction.getFund_id()%></td>
			<td><%=transaction.getTransaction_date()%></td>
			<td><%=transaction.getExecute_date()%></td>
			<td><%=transaction.getShares()%></td>
			<td><%=transaction.getTrasaction_type()%></td>
			<td><%=transaction.getAmount()%></td>
			<td><%=transaction.isIs_complete()%></td>
			<td><%=transaction.isIs_success()%></td>
		</tr>
		<%
			}
			}
		%>

	</table>
</div>