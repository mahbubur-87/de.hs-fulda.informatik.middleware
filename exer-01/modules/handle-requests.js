const math = require('./math.js');
const bh = require('./build-html.js');

module.exports = {
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
		bh.buildIndexPage([dataArr[0], dataArr[1], finalAmount, dataArr[2]], res);
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
