const article = require('../controller/ArticleController');

/*

read an article as HTML text = HTTP GET: https://<host>:<port>/articles/<id>?format=HTML
get an article as a LaTex document = HTTP GET: https://<host>:<port>/articles/<id>?format=LaTex
get a specific page of an article as plain text = HTTP GET: https://<host>:<port>/articles/<id>/pages?pageNumber=<page-number>&format=Plain
to change the abstract of an article = HTTP PUT: https://<host>:<port>/articles/<id>/pages/<id>?contentType=Abstract
to create a new article = HTTP POST: https://<host>:<port>/articles/

*/

module.exports = function(app, conn) {

	article.setConnection(conn);	

	app.route('/articles')
		.post(article.create); // create a new article

	app.route('/articles/:id')
		.get(article.get) // get an article
		.put(article.update); // update an article

	app.route('/articles/:id/pages')
		.get(article.getPages) // get pages of an article
		.post(article.createPage); // create a new page in article

	app.route('/articles/:id/pages/:pageId')
		.put(article.updatePage); // update a specific page of an article
}; 
