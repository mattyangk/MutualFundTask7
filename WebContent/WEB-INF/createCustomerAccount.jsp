<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Create Customer Account</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<form method="POST" action="createCustomerAccount.do">

		<table class="table">

			<tr>
				<td>User Name</td>
				<td><input type="text" name="username" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td>Password</td>
				<td><input type="password" name="password" class="form-control"
					value="" /></td>

			</tr>

			<tr>
				<td>First Name</td>
				<td><input type="text" name="firstname" class="form-control"
					value="" /></td>

			</tr>

			<tr>
				<td>Last Name</td>
				<td><input type="text" name="lastname" class="form-control"
					value="" /></td>

			</tr>

			<tr>
				<td>Address Line1</td>
				<td><input type="text" name="addr_line1" class="form-control"
					value="" /></td>

			</tr>

			<tr>
				<td>Address Line2</td>
				<td><input type="text" name="addr_line2" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td>city</td>
				<td><input type="text" name="city" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td>zip</td>
				<td><input type="text" name="zip" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Create An Customer" /></td>
			</tr>

		</table>

	</form>

</div>
