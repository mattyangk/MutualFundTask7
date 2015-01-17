<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


<jsp:include page="header.jsp" />
<div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
	<h1 class="page-header">Search Fund</h1>
	<jsp:include page="error.jsp" />
<script type="text/javascript" src="http://www.google.com/jsapi"></script>
<script type="text/javascript">
	google.load('visualization', '1', {
		packages : [ 'linechart' ]
	});
	google.setOnLoadCallback(drawVisualization);
	var chart;

	function drawVisualization() {
		// Create and populate the data table.

		var data = new google.visualization.DataTable();
		data.addColumn('string', 'Year');
		data.addColumn('number', 'test1');
		data.addColumn('number', 'test2');
		data.addRows(4);
		data.setValue(0, 0, '2004');
		data.setValue(0, 1, 1000);
		data.setValue(0, 2, 400);
		data.setValue(1, 0, '2005');
		data.setValue(1, 1, 1170);
		data.setValue(1, 2, 460);
		data.setValue(2, 0, '2006');
		data.setValue(2, 1, 860);
		data.setValue(2, 2, 580);
		data.setValue(3, 0, '2007');
		data.setValue(3, 1, 1030);
		data.setValue(3, 2, 540);

		chart = new google.visualization.LineChart(document
				.getElementById('visualization'));
		chart.draw(data, {
			width : 1250,
			height : 300,
			tooltipFontSize : 14,
			max : 1250,
			pointSize : 6,
			legend : 'bottom',
			titleFontSize : 18,
			title : 'tests'
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


	<div id="tfheader">
		<form id="tfnewsearch" method="post" action="c_researchFund.do">
			Research Fund : <input type="text" class="tftextinput"
				name="fundTicker" size="21" maxlength="120"><input
				type="submit" value="search" class="tfbutton">
		</form>
		<div class="tfclear"></div>
	</div>




	<div id="visualization"
		style="margin-left: 200px; width: 300px; height: 300px;"></div>
	

</div>