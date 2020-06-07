const itemListContainer = document.querySelector('#itemList');
const subtotalText = document.querySelector('#subtotal-value');
const taxText = document.querySelector('#tax-value');
const totalCostText = document.querySelector('#total-value');

const cityInput = document.querySelector('#input-city');
const stateInput = document.querySelector('#input-state');
const zipcodeInput = document.querySelector('#input-zipcode');


zipcodeInput.addEventListener('blur', getZipcodeData);

let currentSubtotal = 0;
let currentTaxRate = 0;
let currentTotal = 0;

init();

function init() {
    loadCartList();
    if (zipcodeInput.value) {
        getZipcodeData();
    }
}

function loadCartList() {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(JSON.parse(xhr.responseText));
            let response = JSON.parse(xhr.responseText);
            if (response.cartItems && response.cartItems.length > 0) {
                response.cartItems.forEach(cartItem => {
                    addCartListItem(cartItem.product, cartItem.quantity)
                })
                sessionStorage.setItem('cartItemCount', response.cartItems.length);
            }
        }
    }
    xhr.open("GET", "CartServlet/get", true);
    xhr.send();
}

function addCartListItem(listItem, quantity) {
    let listItemContainer = document.createElement('div');

    let listItemImg = document.createElement('img');
    listItemImg.src = "images/" + listItem.imgSrc;
    listItemContainer.appendChild(listItemImg);

    let listItemName = document.createElement('p');
    listItemName.textContent = listItem.displayName;
    listItemContainer.appendChild(listItemName);

    let liQtyPriceContainer = document.createElement('div');
    liQtyPriceContainer.classList.add('li-qty-price__container');

    let listItemQuantity = document.createElement('p');
    listItemQuantity.textContent = quantity;
    liQtyPriceContainer.appendChild(listItemQuantity);

    let listItemPrice = document.createElement('p');
    listItemPrice.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(listItem.price);
    liQtyPriceContainer.appendChild(listItemPrice);

    listItemContainer.appendChild(liQtyPriceContainer);
    
    listItemContainer.classList.add('li__container');

    itemListContainer.appendChild(listItemContainer);

    addToSubtotal(listItem.price, quantity);
}

function addToSubtotal(itemPrice, quantity) {
    currentSubtotal += quantity * itemPrice;
    calculateTotal();
}

// TODO: refactor to use Java servlets
function getZipcodeData() {
    let zipcode = zipcodeInput.value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(xhr.responseText);
            let response = JSON.parse(xhr.responseText);
            if (response){
                cityInput.value = response.city;
                stateInput.value = response.state;
                currentTaxRate = response.combinedRate;
            }
            else {
                cityInput.value = "";
                stateInput.value = "";
                currentTaxRate = 0;
            }
            calculateTotal()
        }
    }
    xhr.open("GET", `api/zipcode?zipcode=${zipcode}`, true);
    xhr.send();
}

function calculateTotal() {
    let tax = currentSubtotal * currentTaxRate;
    currentTotal = currentSubtotal + tax;
    sessionStorage.setItem('cartTotal', currentTotal); 

    subtotalText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(currentSubtotal);
    taxText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(tax);
    totalCostText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(currentTotal);
}