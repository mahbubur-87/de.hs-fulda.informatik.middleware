module.exports = {
	buildIndexPage: buildIndexPage
};

function buildIndexPage (data, res) {
	res.writeHead(200, {'Content-Type': 'text/html'}); 	
	res.end(loadDataInIndexPage(data));
}

function loadDataInIndexPage (data) {
	return '<html>' + 
				'<head>' + 
					'<title>Tax Calculator</title>' + 
				'</head>' + 
				'<body>' + 
					'<div>' +
						'<h1>Tax Form (WS2017)</h1>' + 
						'<form name="taxForm" method="POST" action="">' + 
						    '<table>' + 
						        '<tbody>' + 
						            '<tr>' + 
						                '<td>Amount:</td>' + 
						                '<td><input name="amount" type="number" required value="' + data[0] + '"></td>' + 
						            '</tr>' + 
						            '<tr>' + 
						                '<td>Tax(%):</td>' + 
						                '<td><input name="tax" type="number" required value="' + data[1] + '"></td>' + 
						            '</tr>' + 
						            '<tr>' + 
						                '<td>Currency:</td>' + 
						                '<td>' + 
						                    '<select name="currency">' + 
						                        '<option value="Dollar"' + (data[3] === 'Dollar' ? 'selected="selected"' : '') + '>Dollar</option>' +
												'<option value="Euro"' + (data[3] === 'Euro' ? 'selected="selected"' : '') + '>Euro</option>' +
						                    '</select>' +  
						                '</td>' + 
						            '</tr>' + 
						            '<tr>' +  
						                '<td><input type="submit" value="Calculate"></td>' + 
						                '<td><input type="reset"></td>' +
						            '</tr>' + 
						        '</tbody>' +         
						    '</table>' + 
						'</form>' + 
					'</div>' + 
					'<div id="result" style="display:inline">' + 
						'<hr>' + 
						'<p>The total amount is ' + data[2] + ' ' + data[3] + '</p>' + 
					'</div>' + 
				'</body>' + 
			'</html>'; 
}
