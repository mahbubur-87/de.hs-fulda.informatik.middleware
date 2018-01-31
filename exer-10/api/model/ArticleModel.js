module.exports.article = {
	
	name: String,
	author: String,
	publishedDate: Date,
	contentType: String, // [HTML, LaTex, Plain]	
	pages: Array // array of pages
}

module.exports.page = {

	number: Number, // page number
	type: String, // page type [Abstract, Introduction, Main Body, Conclusion, Reference]
	content: String,
	size: String, // page size [A4, A5, Letter]
	orientation: String, // page orientation [Landscape, Portrait]
}

module.exports.findById = function(connection, id, callback) {
	
	callback(null, {});
}

module.exports.insert = function(connection, article, callback) {
	
	callback(null, {});
}

module.exports.modify = function(connection, id, article, callback) {
	
	callback(null, {});
}

module.exports.findPages = function(connection, articleId, callback) {
	
	callback(null, []);
}

module.exports.findPageById = function(connection, articleId, pageId, callback) {
	
	callback(null, {});
}

module.exports.insertPage = function(connection, articleId, page, callback) {
	
	callback(null, {});
}

module.exports.modifyPage = function(connection, articleId, pageId, page, callback) {
	
	callback(null, {});
}
