<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Create Fund</h1>
    	<jsp:include page="error.jsp" />
    	<jsp:include page="message.jsp" />
    	<form method="POST" action="createFund.do">

		<table class="table">

			<tr>
				<td>Fund Name</td>
				<td><input type="text" name="fundname" class="form-control"
					value="" /></td>

			</tr>
			<tr>
				<td>Ticker</td>
				<td><input type="text" name="symbol" class="form-control"
					value="" /></td>

			</tr>

			

			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Create A Fund" /></td>
			</tr>

		</table>

	</form>

</div>
    