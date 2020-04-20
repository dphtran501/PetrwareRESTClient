const productGrid = document.querySelector('.productgrid-container');

const jsonObj = load();

init();

function init(){
    populateProductGrid(createProductList(jsonObj));
    removeGridBorderTop();
    removeGridBorderBottom();
    window.addEventListener('resize', onResize);
}

function Product(id, name, description, price, imgSrc){
    this.id = id;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgSrc = imgSrc;
}

function createProductList(jsonObject) {
    var productList = [];
    jsonObject.forEach(item => {
        let name, description;
        if (item.category == "cpu"){
            name = item.brand + " " + item.name;
            description = ["Model: " + item.model, 
                "# of Cores: " + item.numOfCores, 
                "Frequency: " + item.operatingFrequency,
                "Socket Type: " + item.socketType];
        }
        else if (item.category == "ram") {
            name = item.brand + " " + item.series;
            description = ["Model: " + item.model, 
                "Capacity: " + item.capacity,
                "Speed: " + item.speed,
                "Color: " + item.color];
        }
        else if(item.category == "videoCard") {
            name = item.brand + " " + (item.series == "" ? "" : (item.series + " ")) + item.gpu;
            description = ["Model: " + item.model, 
                "Memory Size: " + item.memorySize, 
                "Memory Type: " + item.memoryType,
                "Max GPU Length: " + item.maxGPULength,
                "Dimensions: " + item.cardDimensions];
        }

        let imageSrc = "images/" + item.imgSrc;
        productList.push(new Product(item.model, name, description, item.price, imageSrc));
    })

    return productList;
}

function populateProductGrid(productList) {
    productList.forEach(product => {
        let productCell = createProductCell(product);
        productGrid.appendChild(productCell);
    });
}

function createProductCell(product) {
    let productCell = document.createElement('div');

    let productImg = document.createElement('img');
    productImg.src = product.imgSrc;
    productImg.title = "View details";
    productImg.classList.add('productcell__image');
    productImg.addEventListener('click', onProductClick);
    productCell.appendChild(productImg);

    let priceText = document.createElement('p');
    priceText.textContent = product.price;
    priceText.classList.add('productcell__price');
    productCell.appendChild(priceText);

    let nameText = document.createElement('p');
    nameText.textContent = product.name;
    nameText.classList.add('productcell__name');
    productCell.appendChild(nameText);

    let descriptionList = document.createElement('ul');
    product.description.forEach(attribute => {
        let listItem = document.createElement('li');
        listItem.textContent = attribute;
        descriptionList.appendChild(listItem);
    });
    descriptionList.classList.add('productcell__description');
    productCell.appendChild(descriptionList);

    productCell.classList.add('productcell');

    productCell.id = product.id;

    return productCell;
}

function onProductClick(e) {
    let productCell = e.target.parentElement;
    let product = jsonObj.filter(item => item.model == productCell.id);
    sessionStorage.setItem('product', JSON.stringify(product[0]));
    window.open('product.html', '_self');
}

function onResize(e) {
    removeGridBorderTop();
    removeGridBorderBottom();
}

function removeGridBorderTop() {
    let productGridComputedStyle = window.getComputedStyle(productGrid);
    // grid-template-columns computed value is as specified but converted to absolute lengths (e.g. "300px 300px 300px")
    let numberOfColumns = productGridComputedStyle.getPropertyValue("grid-template-columns").split(" ").length;
    let firstRow = productGrid.querySelectorAll('.productcell:nth-child(-n+' + numberOfColumns + ')');
    firstRow.forEach(productCell => {
        productCell.style.borderTop = "medium none transparent";
    });
    // Cells moving from first to second row get their border tops again
    let secondRow = productGrid.querySelectorAll('.productcell:nth-child(n+' + (numberOfColumns + 1) + '):nth-child(-n+' + (2 * numberOfColumns) + ')');
    secondRow.forEach(productCell => {
        productCell.style.borderTop = "1px solid rgba(0, 62, 120, 0.1)";
    })
}

function removeGridBorderBottom() {
    let productGridComputedStyle = window.getComputedStyle(productGrid);
    // computed value is as specified but converted to absolute lengths (e.g. "300px 300px 300px")
    let numberOfColumns = productGridComputedStyle.getPropertyValue("grid-template-columns").split(" ").length;
    let numberOfRows = productGridComputedStyle.getPropertyValue("grid-template-rows").split(" ").length;
    let lastRow = productGrid.querySelectorAll('.productcell:nth-child(n+' + ((numberOfRows - 1) * numberOfColumns + 1) + '):nth-child(-n+' + (numberOfRows * numberOfColumns) + ')');
    lastRow.forEach(productCell => {
        productCell.style.borderBottom = "medium none transparent";
    });
    let secondLastRow = productGrid.querySelectorAll('.productcell:nth-child(n+' + ((numberOfRows - 2) * numberOfColumns + 1) + '):nth-child(-n+' + ((numberOfRows - 1) * numberOfColumns) + ')');
    secondLastRow.forEach(productCell => {
        productCell.style.borderBottom = "1px solid rgba(0, 62, 120, 0.1)";
    })
}

