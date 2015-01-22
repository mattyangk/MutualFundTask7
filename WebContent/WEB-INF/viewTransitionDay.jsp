<%@ page import="databeans.TransactionBean"%>
<%@ page import="java.util.ArrayList;"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">View Transaction Day History</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<table>
		<tr>
			<td>Transaction ID</td>
			<td>Execute Date</td>
			<td>Shares</td>
			<td>Transaction Type</td>
			<td>Amount</td>
			<td>Status</td>
		</tr>

		<c:forEach var="transaction" items="${transactionsHistory}">
			<tr>
				<c:choose>
				<c:when test="${transaction.trasaction_type.equals('deposit') || transaction.trasaction_type.equals('request')}">
					<td>${transaction.transaction_id}</td>
					<c:choose>
						<c:when test="${empty transaction.execute_date }">
							<td>(pending)</td>
						</c:when>
						<c:otherwise>
							<td>${transaction.execute_date}</td>
						</c:otherwise>
					</c:choose>
					<td>--</td>
					<td>${transaction.trasaction_type}</td>
					<td>${transaction.amount}</td>
					<c:if test="${transaction.is_success}">
						<td>Completed</td>
					</c:if>
					<c:if test="${!transaction.is_complete}">
						<td>Pending</td>
					</c:if>
					<c:if test="${transaction.is_complete && !transaction.is_success}">
						<td>Failed</td>
					</c:if>					
				</c:when>
				<c:otherwise>
					<td>${transaction.transaction_id}</td>
					<c:choose>
						<c:when test="${empty transaction.execute_date }">
							<td>(pending)</td>
						</c:when>
						<c:otherwise>
							<td>${transaction.execute_date}</td>
						</c:otherwise>
					</c:choose>
					<c:choose>
						<c:when test="${empty transaction.shares }">
							<td>(pending)</td>
						</c:when>
						<c:otherwise>
							<td>${transaction.shares}</td>
						</c:otherwise>
					</c:choose>
					<td>${transaction.trasaction_type}</td>
					<td>${transaction.amount}</td>
					<c:if test="${transaction.is_success}">
						<td>Completed</td>
					</c:if>
					<c:if test="${!transaction.is_complete}">
						<td>Pending</td>
					</c:if>
					<c:if test="${transaction.is_complete && !transaction.is_success}">
						<td>Failed</td>
					</c:if>	
				</c:otherwise>
				</c:choose>
			</tr>
		</c:forEach>
	</table>
</div>