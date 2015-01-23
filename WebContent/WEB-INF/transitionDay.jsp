<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<%@ page language="java" contentType="text/html; charset=GB18030"
	pageEncoding="GB18030"%>
<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Transition Day</h1>
	<div>
		<jsp:include page="error.jsp" />
		<jsp:include page="message.jsp" />

			<form action="transitionDayAction.do" method="POST">
				<table class="table">

					<tr>
						<td>Last Trading Day:</td>
						<c:choose>
							<c:when test="${empty theLastDate}">
								<td>No Last Trading Date</td>
							</c:when>
							<c:otherwise>
								<td>${theLastDate}</td>
							</c:otherwise>
						</c:choose>
					</tr>

					<tr>
						<td>Trading Day:</td>
						<td><input type="text" name="transitionDate" value=" " /></td>
					</tr>
				</table>


				<table class="table">
					<thead>
						<tr>
							<th>FundName</th>
							<th>Ticker</th>
							<th>Last trading date</th>
							<th>Last trading price</th>
							<th>New Closing Price</th>
							
						</tr>
					</thead>


					<tbody>
						<c:if test="${requestScope.allFunds!= null}">
							<c:forEach items="${requestScope.allFunds}" var="oneFund">
								<tr>
									<td>${oneFund.fund_name}</td>
									<td>${oneFund.fund_symbol}</td>
									<td>${oneFund.last_date}</td>
									<c:choose>
										<c:when test="${oneFund.last_price == -1.0}">
											<td>Not Available</td>
										</c:when>
										<c:otherwise>
											<td><fmt:formatNumber value="${oneFund.last_price}" type="number"
								maxFractionDigits="2"  minFractionDigits="2" /></td>
										</c:otherwise>
									</c:choose>
									<td><input type="text" name="price" value="" /> <input
										type="hidden" name="fund_id" value="${oneFund.fund_id}" /></td>
								</tr>

							</c:forEach>
						</c:if>

					</tbody>
					<tr><td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Submit" /></td></tr>
					

				</table>


				


			</form></div>

</div>











