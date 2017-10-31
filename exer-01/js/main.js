function resetForm() {
	var frmTax = document.getElementByName('taxForm');
	frmTax['amount'] = frmTax['tax'] = '';
	frmTax['currency'] = 'Dollar';
	document.getElementById('result').style.display = 'none';
}
