module.exports.article = {
	
	name: String,
	author: String,
	publishedDate: Date,
	format: String, // [HTML, LaTex, Plain],
	size: String, // page size [A4, A5, Letter]
	orientation: String, // page orientation [Landscape, Portrait]	
	pages: Array // array of pages
}

module.exports.page = {

	number: Number, // page number
	contentType: String, // page type [Abstract, Introduction, Main Body, Conclusion, Reference]
	content: String,
	lastModified: Date
}

var article = {
	id: 1,	
	name: "RESTful API in Node Js",
	author: "Prof. Dr. Rainer Todtenhöfer",
	publishedDate: new Date(),
	format: "Plain",
	size: "A4",
	orientation: "Portrait",
	pages: [
		{
			number: 1,
			contentType: "Abstract",
			content: "To learn REST web service clearly.",
			lastModified: new Date()
		},
		{
			number: 2,
			contentType: "Introduction",
			content: "RESTful API in Node Js",
			lastModified: new Date()
		},	
		{
			number: 3,
			contentType: "Main Body",
			content: "RESTful API in Node Js",
			lastModified: new Date()
		},	
		{
			number: 4,
			contentType: "Conclusion",
			content: "RESTful API in Node Js",
			lastModified: new Date()
		},	
		{
			number: 5,
			contentType: "Reference",
			content: "https://docs.microsoft.com/en-us/azure/architecture/best-practices/api-design",
			lastModified: new Date()
		}	
	]
};

module.exports.findById = function(connection, id, query, callback) {
	
	article.id = id;

	if (query) {
		if (query.format) {
			article.format = query.format
		}
	}	

	callback(null, article);
}

module.exports.insert = function(connection, article, callback) {
	
	article.id = Math.random() / 100;
	callback(null, article);
}

module.exports.modify = function(connection, id, article, callback) {
	
	article.id = id;
	callback(null, article);
}

module.exports.findPages = function(connection, articleId, query, callback) {
	
	article.id = articleId;

	if (query) {
		
		if (query.format) {
			article.format = query.format
		}
		
		if (query.pageNumber) {
			
			var page = {};

			for (var i = 0; i < article.pages.length; i++) {
				
				if (article.pages[i].number == query.pageNumber) {
					
					page = article.pages[i];
					break;	 
				}			
			}

			callback(null, page);	
			return;
		}

		if (query.contentType) {
			
			var pages = [];

			for (var i = 0; i < article.pages.length; i++) {
				
				if (article.pages[i].contentType == query.contentType) {
					
					pages.push(article.pages[i]);
				}			
			}

			callback(null, pages);	
			return; 
		}
	}

	callback(null, article.pages);
}

module.exports.findPageByNumber = function(connection, articleId, pageNumber, query, callback) {
	
	var resultPage = {};

	for (var i = 0; i < article.pages.length; i++) {
		
		if (article.pages[i].number == pageNumber) {
			
			resultPage = article.pages[i];
			break;
		}			
	}

	if (query) {

		if (query.contentType && resultPage && resultPage.contentType == query.contentType) {
	
			resultPage.content = page.content;
		}
	}
	
	callback(null, resultPage);
}

module.exports.insertPage = function(connection, articleId, page, callback) {
	
	article.id = articleId;
	article.pages[article.pages.length] = page;
	callback(null, article.pages[article.pages.length]);
}

module.exports.modifyPage = function(connection, articleId, pageNumber, query, page, callback) {

	var resultPage = {};

	for (var i = 0; i < article.pages.length; i++) {
		
		if (article.pages[i].number == pageNumber) {
			
			resultPage = article.pages[i];
			break;
		}			
	}

	if (query) {

		if (query.contentType && resultPage && resultPage.contentType == query.contentType) {
	
			resultPage.content = page.content;
		}
	}
		
	callback(null, resultPage);	
}
