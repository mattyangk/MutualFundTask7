<jsp:include page="header.jsp" />

<script src="js/validate.js"></script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Request Check</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />

	<p class="amountInputFeedback" style="color: red"></p>

	<form method="POST" action="requestCheck.do">

		<table class="table">

			<tr>
				<td>Amount</td>
				<td><input type="text" name="amount" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Request Check" /></td>
			</tr>

		</table>

	</form>

</div>
