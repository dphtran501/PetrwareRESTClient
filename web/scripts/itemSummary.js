var fullname = document.getElementById("name")
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
            console.log(xhr.responseText);
            let response = JSON.parse(xhr.responseText);

            fullname.innerHTML = "Name: " + response.firstName + " " + response.lastName
            phone.innerHTML = "Phone: " + response.phone
            email.innerHTML = "Email: " + response.email
            country.innerHTML = "Country: " + response.country
            address.innerHTML = "Address: " + response.address

            let hiddenCardNumber = response.cardNumber.replace(/\d(?=\d{4})/g, "*");
            cardNumber.innerHTML = "Card Number: " + hiddenCardNumber
            shippingMethod.innerHTML = "Shipping Method: " + response.shippingMethod
        }
    }
    xhr.open("GET", "PlacedOrderServlet", true);
    xhr.send();

}


const getItemSummary = function() {
    // items purchase info
    let itemSummaryDiv = document.getElementById('itemSummary');
    let cartTotal = document.getElementById('cartTotal');
    cartTotal.textContent += new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(sessionStorage.getItem('cartTotal'));

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            console.log(JSON.parse(xhr.responseText));
            let response = JSON.parse(xhr.responseText);
            if (response.cartItems && response.cartItems.length > 0) {
                response.cartItems.forEach(cartItem => {
                    let itemName = cartItem.product.displayName;
                    let item = document.createElement("H1");

                    item.textContent = cartItem.quantity + " x " + itemName;
                    itemSummaryDiv.appendChild(item);
                })
            }
        }
    }
    xhr.open("GET", "CartServlet/get", false);
    xhr.send();
}

getItemSummary();