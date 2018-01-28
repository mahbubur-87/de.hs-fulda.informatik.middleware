module.exports = {
	hasAnySequence: hasAnySequence,
	hasSubstring: hasSubstring
};

function hasAnySequence(mainString, sequence) {

	for (var i = 0; i < sequence.length; i++) {
		
		if (!mainString.includes(sequence.charAt(i))) {
			
			return false
		}
	}
	
	return true;
}

function hasSubstring(mainString, sequence) {

	return mainString.includes(sequence);
}
