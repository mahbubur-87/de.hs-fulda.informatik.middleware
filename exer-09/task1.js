var su = require('./modules/string-util');

var mainString = process.argv[2];
var sequence = process.argv[3];

console.log("Main String: " + mainString)
console.log("Sequence: " + sequence)

var hasAnySeq = su.hasAnySequence(mainString, sequence);
var hasSubStr = su.hasSubstring(mainString, sequence);

if (hasAnySeq && !hasSubStr) {
	console.log(1); 
} else if (hasAnySeq && hasSubStr) {
	console.log(2); 
} else {
	console.log(0);
}
