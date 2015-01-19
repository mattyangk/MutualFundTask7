<%@ page import="databeans.PositionAndFundBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
	PositionAndFundBean[] funds = (PositionAndFundBean[]) request.getAttribute("positionAndFunds");
	System.out.println("length: " + funds.length);
%>

<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Sell Fund</h1>
	<jsp:include page="error.jsp" />
	<div></div>
	<script src="//code.jquery.com/jquery-1.10.2.js"></script>
	<script>

	<%
		if (funds != null && funds.length > 0) {
	%>
	$(document).ready ( function () {
		
		$("#fund_id").text("<%=funds[0].getFund_id()%>");
        $("#symbol").text("<%=funds[0].getSymbol()%>");
		$("#shares").text("<%=funds[0].getShares()%>");
		$("#ashares").text("<%=funds[0].getAvailable_shares()%>");
		
		$("select").change(function() {
			var str = "";
			
			$("select option:selected").each(function() {
				str += $(this).text() + " ";
			});
			
			<%
			for (int i = 0; i < funds.length; i++) {
				System.out.println("name: " + funds[i].getName());
			%>
				var name = "<%=funds[i].getName()%>";
				if(str.trim() == name.trim()){
					//alert (str);
					$("#fund_id").text("<%=funds[i].getFund_id()%>");
			        $("#symbol").text("<%=funds[i].getSymbol()%>");
					$("#shares").text("<%=funds[i].getShares()%>");
					$("#ashares").text("<%=funds[i].getAvailable_shares()%>");
				}
			<%
				}
			%>
			
		});
		}
	);
	
	<%
		}
	%>
	

	</script>
	<form method="POST" action="sellFund.do">
		<table class="table">
			<tr>
				<td>Fund Name</td>
				<td>Fund ID</td>
				<td>Ticker</td>
				<td>Shares</td>
				<td>Available Shares</td>
				<td>Shares For Sale</td>
			</tr>
			<tr>
				<td><select id="select">
						<c:choose>
							<c:when test="${not empty positionAndFunds }">
								<c:forEach var="sell" items="${positionAndFunds}">
									<option value="${sell.name}">${sell.name}</option>
								</c:forEach>
							</c:when>
						</c:choose>
				</select></td>
				<td id="fund_id"></td>
				<td id="symbol"></td>
				<td id="shares"></td>
				<td id="ashares"></td>
				<td><input type="text" name="amount" class="form-control" value="" /></td>
				<tr><td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Sell Shares" /></td></tr>

	

		</table>
	</form>
</div>
