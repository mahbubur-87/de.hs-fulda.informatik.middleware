var chkSeq = require('./modules/CheckSequence');
var chkSub = require('./modules/CheckSubstring');

var mainString = 'somefxuxdxlxamm';
var sequence = 'fulda';

var hasAnySeq = chkSeq.hasAnySequence(mainString, sequence);
var hasSubStr = chkSub.hasSubstring(mainString, sequence);

if (hasAnySeq && !hasSubStr) {
	console.log(1);		
	reutrn;         
}

if (hasAnySeq && hasSubStr) {
	console.log(2);		
	reutrn;         
}

console.log(0);
