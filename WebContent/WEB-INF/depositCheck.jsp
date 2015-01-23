<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<jsp:include page="header.jsp" />

<script src="js/validate.js"></script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Deposit Check</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />


	<p class="amountInputFeedback" style="color: red"></p>

	<form method="POST" action="depositCheck.do">
		<table class="table">
			<tr>
				<td>Customer Username</td>
				<td><input type="text" name="username" class="form-control"
					value="" /></td>
			</tr>
			<tr>
				<td>Deposit Amount</td>
				<td><input type="text" name="amount" class="form-control"
					value="" /></td>
			</tr>
			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Deposit Check" /></td>
			</tr>
		</table>
	</form>
</div>