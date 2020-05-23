var name = document.getElementById("name")
var phone = document.getElementById("phone")
var email = document.getElementById("email")
var country = document.getElementById("country")
var address = document.getElementById("address")
var cardNumber = document.getElementById("cardNumber")
var shippingMethod = document.getElementById("shippingMethod")

window.addEventListener('load', onLoad);


function onLoad() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        // 4 means finished, and 200 means okay.
        if (xhr.readyState === 4 && xhr.status === 200) {
            // console.log(xhr.responseText);
            let response = JSON.parse(xhr.responseText);

            name.innerHTML = "Name: "+ response.firstName + " " + response.lastName
            phone.innerHTML = "Phone: " + response.phone
            email.innerHTML = "Email: " + response.email
            country.innerHTML = "Country: " + response.country
            address.innerHTML = "Address: " + response.address

            let hiddenCardNumber = response.cardNumber.replace(/\d(?=\d{4})/g, "*");
            cardNumber.innerHTML = "Card Number: " + hiddenCardNumber
            shippingMethod.innerHTML = "Shipping Method: " + response.shippingMethod
        }
    }
    xhr.open("GET", "CheckoutServlet", true);
    xhr.send();

}


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