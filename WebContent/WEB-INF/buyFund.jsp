<%@ page import="databeans.FundBean"%>
<%@ page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
	
<script src="js/validate.js"></script>

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Buy Fund</h1>
	<jsp:include page="error.jsp" />
	<jsp:include page="message.jsp" />

	<p  class="amountInputFeedback" style= "color : red"> </p>
	<form method="POST" action="buyFund.do">
		<table class="table">
			<tr>
				<td>
					Fund Name
				</td>
			
				<td><select name="fundname">
						<%
							FundBean[] funds = (FundBean[]) request.getAttribute("funds");
							if (funds != null) {
								for (FundBean fund : funds) {
						%>
						<option value="<%=fund.getName()%>"><%=fund.getName()%>_<%=fund.getSymbol()%></option>
						<%
							}
							}
						%>
				</select></td>
			</tr>

			<tr>

				<td>Amount</td>
				<td><input type="text" name="amount" class="form-control"
					value="" style="width: 20%" />

				</td>

			</tr>

			<tr>
				<td colspan="2" align="left"><input type="submit"
					name="button" class="btn btn-success" value="Buy Fund" /></td>
			</tr>
		</table>
	</form>
</div>
