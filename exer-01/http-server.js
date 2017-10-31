const http = require('http');
const url = require('url');
const fs = require('fs');
const hr = require('./modules/handle-requests.js');

http.createServer(function (req, res) {
	req.on('error', function (err) {
		console.log('request error: ', err);
		res.writeHead(400);
		res.end();
	});

	res.on('error', function (err) {
		console.log('response error: ', err);
		res.writeHead(500);
		res.end();
	});

	var urlObj = url.parse(req.url);

	switch (req.method) {
		case 'GET': res.writeHead(200, {'Content-Type': 'text/html'});
					fs.createReadStream('html/index.html')
						.pipe(res);		

					return;	

		case 'POST': hr.handlePost(req, res);
					 return;
	}

	res.writeHead(404);
	res.end();
})
.listen(Number(process.argv[2]));
