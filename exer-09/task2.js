const http = require('http');
const hr = require('./modules/request-handler.js');

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

	switch (req.method) {
		case 'GET': hr.handleGet(req, res)		
					return;	

		case 'POST': hr.handlePost(req, res);
					 return;
	}

	res.writeHead(404);
	res.end();
})
.listen(Number(process.argv[2]));
