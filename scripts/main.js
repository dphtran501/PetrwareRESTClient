let defaultMaxRows = 5;
let defaultMaxColumns = 5;

const productGrid = document.querySelector('.productgrid-container');

setProductGrid(defaultMaxColumns, defaultMaxRows, testFillList(25));

function setProductGrid(numberOfColumns, numberOfRows, productList) {
    let currIdx = 0;
    for (let row = 1; row <= numberOfRows; row++){
        for (let col = 1; col <= numberOfColumns; col++){
            if (currIdx == productList.length) break;
            let productCell = createProductCell(productList[currIdx++])
            productCell.setAttribute("grid-column", col);
            productCell.setAttribute("grid-row", row);
            productGrid.appendChild(productCell);
        }
        if (currIdx == productList.length) break;
    }
}

function createProductCell(product) {
    let productCell = document.createElement('div');

    let productImg = document.createElement('img');
    productImg.src = product.imgSrc;
    productImg.classList.add('productimage');
    productCell.appendChild(productImg);

    let priceText = document.createElement('p');
    priceText.textContent = product.price;
    priceText.classList.add('productprice');
    productCell.appendChild(priceText);

    let nameText = document.createElement('p');
    nameText.textContent = product.name;
    nameText.classList.add('productname');
    productCell.appendChild(nameText);

    let descriptionText = document.createElement('p');
    descriptionText.textContent = product.description;
    descriptionText.classList.add('productdescription');
    productCell.appendChild(descriptionText);

    productCell.addEventListener('click', onProductClick);

    productCell.classList.add('productcell');

    return productCell;
}

function onProductClick(e) {
    let productCell = e.currentTarget;
    let nameText = productCell.querySelector('.productname');
    let descriptionText = productCell.querySelector('.productdescription');
    let priceText = productCell.querySelector('.productprice');
    let image = productCell.querySelector('.productimage')
    let product = new Product(nameText.textContent, descriptionText.textContent, priceText.textContent, image.src);
    sessionStorage.setItem('product', JSON.stringify(product));
    window.open('product.html', '_self');
}

function Product(name, description, price, imgSrc){
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgSrc = imgSrc;
}

function testFillList(numberOfProducts) {
    var productList = [];
    for (let i = 0; i < numberOfProducts; i++) {
        productList.push(new Product("Test" + i, "This is test product " + i + ".", "$0.00", "images/default-product.png"))
    }
    return productList;
}