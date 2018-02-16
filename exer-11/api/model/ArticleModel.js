var article = {

	id: Number,	
	name: String,
	author: String,
	publishedDate: Date,
	pages: Array // array of pages
}

var page = {

	id: Number,
	number: Number, // page number
	format: String, // [HTML, LaTex, Plain],
	size: String, // page size [A4, A5, Letter]
	orientation: String, // page orientation [Landscape, Portrait]	
	contentType: String, // page type [Abstract, Introduction, Main Body, Conclusion, Reference]
	content: String,
	lastModified: Date
}

module.exports.Article = article

module.exports.Page = page

module.exports.find = function(connection, id, query, callback) {
	
	var sql = "CALL sp_article(".concat(
		"'SELECT_DETAIL', ", id, ", NULL, NULL, NULL, '", query.format, "')"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			var article = result[0][0];

			article.pages = result[1];

			callback(null, article);	
		});
	});
}

module.exports.insert = function(connection, article, callback) {

	var sql = "CALL sp_article(".concat(
		"'INSERT', NULL, '", article.name, "', '",  article.author, "', '", article.publishedDate, "', NULL)"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			callback(null, result[0][0]);	
		});
	});
}

module.exports.modify = function(connection, id, article, callback) {
	
	var sql = "CALL sp_article(".concat(
		"'UPDATE', ", id, ", '", article.name, "', '",  article.author, "', '", article.publishedDate, "', NULL)"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			callback(null, result[0][0]);	
		});
	});
}

module.exports.findPages = function(connection, articleId, query, callback) {
	
	var sql = "CALL sp_article_page(".concat(
		"'SELECT_BY_KEY', NULL, ", articleId, ", " , query.pageNumber, ", '",  query.format, "', NULL, NULL, NULL, NULL)"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			callback(null, result[0]);	
		});
	});
}

module.exports.insertPage = function(connection, articleId, page, callback) {

	var sql = "CALL sp_article_page(".concat(
		"'INSERT', NULL, ", articleId, ", " , page.number, ", '",  page.format, "', '", page.size, "', '",  page.orientation, "', '",   			page.contentType, "', '", page.content, "')"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			callback(null, result[0][0]);	
		});
	});
}

module.exports.modifyPage = function(connection, articleId, pageId, page, callback) {

	var sql = "CALL sp_article_page(".concat(
		"'UPDATE', ", pageId, ", ", articleId, ", " , page.number, ", '",  page.format, "', '", page.size, "', '",  page.orientation, "', 			'", page.contentType, "', '", page.content, "')"
	);

	process.nextTick(function() {
		
		connection.query(sql, function(err, result) {
		
			if (err) {
				callback(err, null);
			}

			callback(null, result[0][0]);	
		});
	});	
}
