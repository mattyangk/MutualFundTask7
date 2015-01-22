<%@page import="databeans.FundBean"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="databeans.FundPriceDetailBean" %>

<%
	FundBean fund = (FundBean) request.getAttribute("fund");
	FundPriceDetailBean[] priceList = (FundPriceDetailBean[]) request.getAttribute("priceList");
%>

<jsp:include page="header.jsp" />
<jsp:include page="message.jsp" />

<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Research Fund</h1>
	<jsp:include page="error.jsp" />
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<% if (fund != null) {%>
<script type="text/javascript">
	google.load('visualization', '1', {
		packages : [ 'corechart' ]
	});
	google.setOnLoadCallback(drawVisualization);
	var chart;

	function drawVisualization() {
		// Create and populate the data table.

		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Year');
		data.addColumn('number', '<%= fund.getSymbol()%>');
		//alert(<%= priceList.length%>);
		data.addRows(<%= priceList.length%>);
		<% 
			for (int i = 0; i < priceList.length; i++) {
				
		%>
		
		data.setValue(<%=i%>, 0, '<%=priceList[i].getPrice_date().getYear() + 1900 + "-" + (priceList[i].getPrice_date().getMonth() + 1) + "-" + priceList[i].getPrice_date().getDate()%>');
		data.setValue(<%=i%>, 1, <%=priceList[i].getPrice()%>);
		<%
			}
		%>

		chart = new google.visualization.LineChart(document
				.getElementById('visualization'));
		chart.draw(data, {
			width : 900,
			height : 400,
			tooltipFontSize : 14,
			max : 200,
			pointSize : 6,
			legend : 'right',
			titleFontSize : 18,
			title : 'Price History of <%= fund.getName() %>',
			is3D : true
		});

		google.visualization.events.addListener(chart, 'onmouseover',
				barMouseOver);
		google.visualization.events.addListener(chart, 'onmouseout',
				barMouseOut);
	}

	// Add our over/out handlers.

	function barMouseOver(e) {
		chart.setSelection([ e ]);
	}

	function barMouseOut(e) {
		chart.setSelection([ {
			'row' : null,
			'column' : null
		} ]);
	}
</script>

<% } %>


	<div id="visualization"
		style="margin-left: 50px; margin-top: 20px; width: 900px; height: 400px;"></div>
		

	

</div>