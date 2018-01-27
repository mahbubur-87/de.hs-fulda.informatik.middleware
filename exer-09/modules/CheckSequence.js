module.exports = {
	hasAnySequence: hasAnySequence
};

function hasAnySequence(mainString, sequence) {
	var pattern = '/('.concat(sequence.charAt(0));
	for (var i = 1; i < sequence.length; i++) {
		pattern = pattern.concat('|').concat(sequence.charAt(i));
	}
	pattern = pattern.concat(')').concat('{').concat(sequence.length).concat('}/');	
	console.log(pattern);
	var regEx = new RegExp(pattern);
	return regEx.test(mainString);
}
