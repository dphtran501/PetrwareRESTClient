const productImage = document.querySelector('#productimage');
const productPriceText = document.querySelector('#productprice');
const productNameText = document.querySelector('#productname');
const productionDescriptionText = document.querySelector('#productdetails');

window.addEventListener('load', onLoad);

function onLoad() {
    let product = JSON.parse(sessionStorage.getItem('product'));
    productImage.src = product.imgSrc;
    productPriceText.textContent = product.price;
    productNameText.textContent = product.name;
    productionDescriptionText.textContent = product.description;
}