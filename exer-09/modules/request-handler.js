const url = require('url');
const fs = require('fs');
const math = require('./math.js');
const hb = require('./html-builder.js');
const fh = require('./file-handler.js')

module.exports = {
	handleGet: handleGet,
	handlePost: handlePost
};

function handlePost (req, res) {
	var finalAmount, data = '', dataArr = new Array();	

	req.setEncoding('UTF-8');	

	req.on('data', function (chunk) {
		data += chunk;
	});

	req.on('end', function () {
		dataArr = parseData(data);
		finalAmount = math.calculateTotalWithTax(dataArr[0], dataArr[1]);
		const fileContent = [dataArr[0], dataArr[1], finalAmount, dataArr[2], Date(), req.headers['user-agent']]
		fh.writeToFile(fileContent);
		hb.buildIndexPage([dataArr[0], dataArr[1], finalAmount, dataArr[2]], res);
	});
}

function handleGet(req, res) {
	
	const urlObj = url.parse(req.url);

	switch(urlObj.pathname) {
		
		case '/search': res.writeHead(200, {'Content-Type': 'application/json'});
						getByCurrency(urlObj.search.split("=")[1], res);
						break;

		default: res.writeHead(200, {'Content-Type': 'text/html'});
				 fs.createReadStream('html/index.html')
					.pipe(res);
	}
}

function getByCurrency(currency, res) {
	
	fh.readFromFile(function(err, data) {
		
		if (err) {
			console.log("File Read Error: ", err);
			res.end("[]");
			return;
		}

		data = data.split("\n");
		
		var record, result = [];
		
		for (var i = 0; i < (data.length - 1); i++) {
			
			record = JSON.parse(data[i]);
			
			if (currency == record.currency) {
				result.push(record);
			}			
		}

		res.end(JSON.stringify(result));
	});	
}

function parseData (data) {
	var datArr = new Array();

	data.split('&')
		.forEach(function (value) {
			datArr.push(value.split('=')[1]);			
		});

	datArr[0] = Number(datArr[0]);
	datArr[1] = Number(datArr[1]);

	return datArr;
}
