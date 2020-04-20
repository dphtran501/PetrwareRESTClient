const productImage = document.querySelector('#productimage');
const productPriceText = document.querySelector('#productprice');
const productNameText = document.querySelector('#productname');
const productModelText = document.querySelector('#productmodel');
const productionDescriptionText = document.querySelector('#productdetails');
const addButton = document.querySelector('#addbutton');

window.addEventListener('load', onLoad);
addButton.addEventListener('click', onAddClick);

function onLoad() {
    let product = JSON.parse(sessionStorage.getItem('product'));
    productImage.src = "images/" + product.imgSrc;
    productPriceText.textContent = product.price;
    if (product.category == "cpu"){
        productNameText.textContent = product.brand + " " + product.name;
    }
    else if (product.category == "ram") {
        productNameText.textContent = product.brand + " " + product.series;
    }
    else if(product.category == "videoCard") {
        productNameText.textContent = product.brand + " " + (product.series == "" ? "" : (product.series + " ")) + product.gpu;
    }
    productModelText.textContent = "Model: " + product.model;
    productionDescriptionText.textContent = product.description;
}

function onAddClick() {
    let cartList = [];
    let cartData = sessionStorage.getItem('cartData');
    if (cartData != null) {
        cartList = JSON.parse(cartData);
    }

    let product = JSON.parse(sessionStorage.getItem('product'));
    cartList.push(product);
    sessionStorage.setItem('cartData', JSON.stringify(cartList));
}