const article = require('../model/ArticleModel');

module.exports = {
	
	setConnection: setConnection,
	get: get,
	create: create,
	update: update,
	getPages: getPages,
	createPage: createPage,
	updatePage: updatePage
}

var connection, errorMessage = {error: "An internal error has been occured. Please see server log / console."};

function setConnection(conn) {
	connection = conn
}

function get(req, res) {
	
	var id = req.params.id;

	article.find(connection, id, req.query, function(err, article) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(article);
	});
}

function create(req, res) {

	article.insert(connection, req.body, function(err, article) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(article);
	});
}

function update(req, res) {

	var id = req.params.id;

	article.modify(connection, id, req.body, function(err, article) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(article);
	});
}

function getPages(req, res) {

	var articleId = req.params.id;

	article.findPages(connection, articleId, req.query, function(err, pages) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(pages);
	});
}

function createPage(req, res) {

	var articleId = req.params.id;
	
	article.insertPage(connection, articleId, req.body, function(err, page) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(page);
	});
}

function updatePage(req, res) {

	var articleId = req.params.id;
	var pageId = req.params.pageId;

	article.modifyPage(connection, articleId, pageId, req.body, function(err, page) {
		
		if (err) {
			
			console.log("Error: ", err);
			res.json(errorMessage);
		}
		
		res.json(page);
	});
}
