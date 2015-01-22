<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Transition Day</h1>
	<div>
		<jsp:include page="error.jsp" />
		<jsp:include page="message.jsp">

			<form action="transitionDayAction.do" method="POST">
				<table>

					<tr>
						<td>Last Trading Day:</td>
						<td>${theLastDate}</td>
					</tr>

					<tr>
						<td>Trading Day:</td>
						<td><input type="text" name="transitionDate" value=" " /></td>
					</tr>
				</table>


				<table>
					<thead>
						<tr>
							<th style="width: 100px">FundName</th>
							<th style="width: 100px">Ticker</th>

							<th style="width: 130px">New Closing Price</th>
						</tr>
					</thead>


					<tbody>
						<c:if test="${requestScope.allFunds!= null}">
							<c:forEach items="${requestScope.allFunds}" var="oneFund">
								<tr>
									<td>${oneFund.fund_name}</td>
									<td>${oneFund.fund_symbol}</td>

									<td><input type="text" name="price" value="" /> <input
										type="hidden" name="fund_id" value="${oneFund.fund_id}" /></td>
								</tr>

							</c:forEach>
						</c:if>

					</tbody>

				</table>


				<input type="submit" name="submit" value="Submit">

			</form></div>

</div>











