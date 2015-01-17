<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Matt Yang's Account</h1>
	<jsp:include page="error.jsp" />

	<div class="row placeholders">
		<div class="table-responsive">
			<table class="table table-striped">
				<thead>
					<tr>
						<th style="text-align: center">Personal Information</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>Address</td>
						<td>6315 Forbes Avenue</td>
					</tr>

					<tr>
						<td>Last Trading Date</td>
						<td>January 3 2015</td>
					</tr>

					<tr>
						<td>Cash Balance</td>
						<td>$181790.02</td>
					</tr>
				</tbody>
			</table>

			<hr />

			<table class="table table-striped">
				<thead>
					<tr>
						<th style="text-align: center">Fund Name</th>
						<th style="text-align: center">Share of Fund</th>
					</tr>
				</thead>
				<tbody>

					<tr>
						<td>Fund A</td>
						<td>120</td>
					</tr>

					<tr>
						<td>Fund B</td>
						<td>200</td>
					</tr>

					<tr>
						<td>Fund C</td>
						<td>350</td>
					</tr>
				</tbody>
			</table>
		</div>

	</div>
</div>