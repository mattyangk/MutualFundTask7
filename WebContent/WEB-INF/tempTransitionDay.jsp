<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Transition Day</h1>
	<jsp:include page="error.jsp" />
	<form method="POST" action="tempTransitionDay.do">

		<table class="table">

			<tr>
				<td>Closing Date</td>
				<td><input type="date" name="transitionDate" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Transition Day" /></td>
			</tr>

		</table>

	</form>

</div>
