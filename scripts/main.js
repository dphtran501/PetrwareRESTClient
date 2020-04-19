let defaultMaxRows = 2;
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
            description = "# of Cores: " + item.numOfCores + "\r\n" 
                + "Frequency: " + item.operatingFrequency + "\r\n"
                + "Socket Type: " + item.socketType;
        }
        else if (item.category == "ram") {
            name = item.brand + " " + item.series;
            description = "Capacity: " + item.capacity + "\r\n" 
                + "Speed: " + item.speed + "\r\n" 
                + "Color: " + item.color;
        }
        else if(item.category == "videoCard") {
            name = item.brand + " " + (item.series == "" ? "" : (item.series + " ")) + item.gpu;
            description = "Memory Size: " + item.memorySize + "\r\n" 
                + "Memory Type: " + item.memoryType + "\r\n" 
                + "Max GPU Length: " + item.maxGPULength + "\r\n" 
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
            if (row == 1) {
                productCell.style.borderTop = "medium none transparent";
            }
            else if(row == numberOfRows) {
                productCell.style.borderBottom = "medium none transparent";
            }
            productGrid.appendChild(productCell);
        }
        if (currIdx == productList.length) break;
    }
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

    let descriptionText = document.createElement('p');
    descriptionText.textContent = product.description;
    descriptionText.classList.add('productcell__description');
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

