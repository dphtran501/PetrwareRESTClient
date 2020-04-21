const itemUL = document.querySelector('#itemList');
const totalCost = document.querySelector('#totalCost')

loadCartList();

function loadCartList() {
    let cartData = sessionStorage.getItem('cartData');
    if (cartData != null) {
        let cartList = JSON.parse(cartData);
        cartList.forEach(item => addCartListItem(item));
    }
}

function addCartListItem(listItem) {
    let listItemContainer = document.createElement('div');

    let listItemImg = document.createElement('img');
    listItemImg.src = "images/" + listItem.imgSrc;
    listItemContainer.appendChild(listItemImg);

    let listItemName = document.createElement('p');
    if (listItem.category == "cpu"){
        listItemName.textContent = listItem.brand + " " + listItem.name;
    }
    else if (listItem.category == "ram") {
        listItemName.textContent = listItem.brand + " " + listItem.series;
    }
    else if(listItem.category == "videoCard") {
        listItemName.textContent = listItem.brand + " " + (listItem.series == "" ? "" : (listItem.series + " ")) + listItem.gpu;
    }
    listItemContainer.appendChild(listItemName);

    let listItemQuantity = document.createElement('p');
    listItemQuantity.textContent = listItem.quantity;
    listItemContainer.appendChild(listItemQuantity);

    let listItemPrice = document.createElement('p');
    listItemPrice.textContent = listItem.price;
    listItemContainer.appendChild(listItemPrice);

    listItemContainer.classList.add('li__container');

    itemUL.appendChild(listItemContainer);

    addToTotalCost(listItem.price, listItem.quantity);
}

function addToTotalCost(itemPrice, quantity) {
    let currentTotalCost = (totalCost.textContent != "") ?  parseFloat(totalCost.textContent.substring(1).replace(/,/g, '')) : 0.00;
    currentTotalCost += parseFloat(quantity) * parseFloat(itemPrice.substring(1).replace(/,/g, ''));
    //totalCost.textContent = "$" + currentTotalCost.toFixed(2);
    totalCost.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(currentTotalCost);
}