/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function addToCart (index) 
{   
    var itm = document.getElementById("itmList").rows[index].cells;
    var postData = "action=ADD&name=" + itm[0].innerHTML + "&price=" + itm[1].innerHTML + 
                    "&quantity=" + document.getElementById("itm" + index + "Qty").value;
    var xhttp = new XMLHttpRequest();
    
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
           // Typical action to be performed when the document is ready:
           //document.getElementById("demo").innerHTML = xhttp.responseText;
        }
    };
    xhttp.open("POST", "ShoppingCart", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send(postData);
}

