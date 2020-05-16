const getItemSummary = function() {
    // items purchase info
    let itemSummaryDiv = document.getElementById('itemSummary');
    let cartTotal = document.getElementById('cartTotal');
    cartTotal.textContent += new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(sessionStorage.getItem('cartTotal'));

    let cID = sessionStorage.getItem('cID');
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            console.log(JSON.parse(xhr.responseText));
            let response = JSON.parse(xhr.responseText);
            if (response.length > 0) {
                response.forEach(cartItem => {
                    let itemName;
                    let item = document.createElement("H1");
                    if (cartItem.category == "cpu"){
                        itemName = createProductName([cartItem.brand, cartItem.name]);
                    }
                    else if (cartItem.category == "ram") {
                        itemName = createProductName([cartItem.brand, cartItem.series]);
                    }
                    else if(cartItem.category == "videoCard") {
                        itemName = createProductName([cartItem.brand, cartItem.series, cartItem.gpu]);
                    }

                    item.textContent = cartItem.quantity + " x " + itemName;
                    itemSummaryDiv.appendChild(item);
                })
            }
        }
    }
    xhr.open("GET", `db_cart_query.php?cID=${cID}`, false);
    xhr.send();
}

function createProductName(attributeList) {
    let name = "";
    for (let i = 0; i < attributeList.length; i++) {
        name += ((attributeList[i] === null) ? "" : attributeList[i]);
        if (i < attributeList.length - 1){
            name += " ";
        }
    }

    return name;
}

getItemSummary();