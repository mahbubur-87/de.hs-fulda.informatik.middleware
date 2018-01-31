const express = require('express');
const bodyParser = require('body-parser');
const route = require('./api/route/ArticleRoute');

app = express();

app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

route(app, null);

app.use(function(req, res) {
  
	res.status(404).send({url: req.originalUrl + ' not found'})
});

var port = process.argv[2] || 18010;
app.listen(port);

console.log("RESTful API Server is running on port: " + port)
