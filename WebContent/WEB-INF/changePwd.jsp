<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />


<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Change Password</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />
	<form method="POST" action="changePwd.do">

		<table class="table">

			<tr>
				<td>New Password</td>
				<td><input type="password" name="newPassword" class="form-control"
					value="" /></td>
			</tr>

			<tr>
				<td>Confirm Password</td>
				<td><input type="password" name="rePassword" class="form-control"
					value="" /></td>

			</tr>

			

			<tr>
				<td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Reset Password" /></td>
			</tr>

		</table>

	</form>

</div>