const itemListContainer = document.querySelector('#itemList');
const totalCostText = document.querySelector('#totalCost');

const cityInput = document.querySelector('#input-city');
const stateInput = document.querySelector('#input-state');
const zipcodeInput = document.querySelector('#input-zipcode');

zipcodeInput.addEventListener('blur', onBlur);

let currentTotalCost = 0;

loadCartList();

function loadCartList() {
    let cartData = sessionStorage.getItem('cartData');
    if (cartData != null) {
        let cartList = JSON.parse(cartData);
        cartList.forEach(itemQueryId => {
            var xhr = new XMLHttpRequest();
            xhr.onreadystatechange = function() {
                // 4 means finished, and 200 means okay.
                if (xhr.readyState == 4 && xhr.status == 200) {
                    let response = JSON.parse(xhr.responseText);
                    let item = response[0];
                    item.quantity = itemQueryId.quantity;
                    addCartListItem(item);
                }
            }
            xhr.open("GET", `db_product_query.php?id=${itemQueryId.id}&category=${itemQueryId.category}`, true);
            xhr.send();
        });
    }
}

function addCartListItem(listItem) {
    let listItemContainer = document.createElement('div');

    let listItemImg = document.createElement('img');
    listItemImg.src = "images/" + listItem.imgSrc;
    listItemContainer.appendChild(listItemImg);

    let listItemName = document.createElement('p');
    if (listItem.category == "cpu"){
        listItemName.textContent = createProductName([listItem.brand, listItem.name]);
    }
    else if (listItem.category == "ram") {
        listItemName.textContent = createProductName([listItem.brand, listItem.series]);
    }
    else if(listItem.category == "videoCard") {
        listItemName.textContent = createProductName([listItem.brand, listItem.series, listItem.gpu]);
    }
    listItemContainer.appendChild(listItemName);

    let liQtyPriceContainer = document.createElement('div');
    liQtyPriceContainer.classList.add('li-qty-price__container');

    let listItemQuantity = document.createElement('p');
    listItemQuantity.textContent = listItem.quantity;
    liQtyPriceContainer.appendChild(listItemQuantity);

    let listItemPrice = document.createElement('p');
    listItemPrice.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(listItem.price);
    liQtyPriceContainer.appendChild(listItemPrice);

    listItemContainer.appendChild(liQtyPriceContainer);
    
    listItemContainer.classList.add('li__container');

    itemListContainer.appendChild(listItemContainer);

    addToTotalCost(listItem.price, listItem.quantity);
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

function addToTotalCost(itemPrice, quantity) {
    currentTotalCost += quantity * itemPrice;
    totalCostText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(currentTotalCost);
}

function onBlur(e) {
    let zipcode = zipcodeInput.value;

    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (xhr.readyState == 4 && xhr.status == 200) {
            let response = JSON.parse(xhr.responseText);
            let data = (response.length > 0) ? response[0] : null;
            if (data){
                cityInput.value = data.city;
                stateInput.value = data.state;
            }
        }
    }
    xhr.open("GET", `db_form_query.php?zipcode=${zipcode}`, true);
    xhr.send();
}