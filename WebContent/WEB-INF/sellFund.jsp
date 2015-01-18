<%@ page import="databeans.FundBean" %>
<%@ page import="java.util.ArrayList" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Sell Fund</h1>
	<jsp:include page="error.jsp" />
	
	<form method="POST" action="sellFund.do">
	<table class="table">
	<tr>
    <td>
    <select name＝"sellname">
     <%
			FundBean[] sells = (FundBean[]) request
					.getAttribute("sells");
			if (sells!= null) {
				for (FundBean sell : sells) {
		%>
	 <option value=<%=sell.getName() %>><%=sell.getName() %>_<%=sell.getSymbol() %></option>
	 <%}
			}
	 %>
     </select>
     </td>
      	
	<td>Share</td>
	<td><input type="text" name="share" class="form-control"
					value="" /></td>
     </tr>
     <tr>
     <td colspan="2" align="center"><input type="submit"
					name="button" class="btn btn-success" value="Sell Fund" /></td>
	 </tr>
     </table>
     </form>
 </div>
