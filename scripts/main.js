let defaultMaxRows = 5;
let defaultMaxColumns = 5;

const productGrid = document.querySelector('.productgrid-container');

const jsonObj = load();

setProductGrid(defaultMaxColumns, defaultMaxRows, createProductList(jsonObj));

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
            description = "# of Cores: " + item.numOfCores + "\n" 
                + "Frequency: " + item.operatingFrequency + "\n"
                + "Socket Type: " + item.socketType;
        }
        else if (item.category == "ram") {
            name = item.brand + " " + item.series;
            description = "Capacity: " + item.capacity + "\n" 
                + "Speed: " + item.speed + "\n" 
                + "Color: " + item.color;
        }
        else if(item.category == "videoCard") {
            name = item.brand + " " + (item.series == "" ? "" : (item.series + " ")) + item.gpu;
            description = "Memory Size: " + item.memorySize + "\n" 
                + "Memory Type: " + item.memoryType + "\n" 
                + "Max GPU Length: " + item.maxGPULength + "\n" 
                + "Dimensions: " + item.cardDimensions;
        }

        let imageSrc = "images/" + item.imgSrc;
        productList.push(new Product(item.model, name, description, item.price, imageSrc));
    })

    return productList;
}

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
    productImg.addEventListener('click', onProductClick);
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

