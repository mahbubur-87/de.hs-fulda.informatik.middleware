const fs = require('fs')
const filePath = "tax-records.txt"

module.exports = {
	writeToFile: writeToFile,
	readFromFile: readFromFile
}

function writeToFile(data) {

	data = {
		amount: data[0],
		taxRate: data[1],
		totalAmount: data[2],
		currency: data[3],
		date: data[4],
		userAgent: data[5]
	}; 	

	fs.appendFile(filePath, JSON.stringify(data) + "\n", (err) => {
		
		if (err) {
			console.log("File Append Error: ", err);
		}

		console.log("Data is saved successfuly.");
	});
}

function readFromFile(callback) {
	
	fs.readFile(filePath, 'utf-8', callback);
}
