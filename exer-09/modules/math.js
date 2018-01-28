module.exports = {
	calculateTotalWithTax: calculateTotalWithTax	
};

function calculateTotalWithTax (amount, taxPercentage) {
	return amount + (amount * taxPercentage * 0.01);
}
