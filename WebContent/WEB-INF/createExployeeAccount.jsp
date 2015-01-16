<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Create Employee Account</h1>
</div>
<form>
	<table>
		<tr>
			<td>User Name</td>
			<td>
				<div class="form-group">
					<input type="text" name="userName" class="form-control" value="" />
			</td>
			</div>
		</tr>
		<tr>
			<td>First Name</td>
			<td><div class="form-group">
					<input type="text" name="firstName" class="form-control" value="" /></td>
			</div>
		</tr>

		<tr>
			<td>Last Name</td>
			<td><div class="form-group">
					<input type="text" name="lastName" class="form-control" value="" /></td>
			</div>
		</tr>
		<tr>
			<td>Password</td>
			<td><div class="form-group">
					<input type="password" name="password" class="form-control"
						value="" /></td>
			</div>
		</tr>
		<tr>
			<td colspan="2" align="right"><input type="submit" name="button"
				class="btn btn-success" value="Create An Employee" /></td>
		</tr>
	</table>
</form>