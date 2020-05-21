const productGrid = document.querySelector('.productgrid-container');
const searchForm = document.querySelector('#search-form');
const searchInput = document.querySelector('#search-input');

init();

function init(){
    populateProductGrid();
    searchForm.addEventListener('submit', onSubmit);
    window.addEventListener('resize', onResize);
}

function Product(id, category, name, description, price, imgSrc){
    this.id = id;
    this.category = category;
    this.name = name;
    this.description = description;
    this.price = price;
    this.imgSrc = imgSrc;
}

function onSubmit(e) {
    e.preventDefault();
    clearProductGrid();
    populateProductGrid(searchInput.value);
}

function populateProductGrid(searchQuery) {
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        // 4 means finished, and 200 means okay.
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(xhr.responseText);
            let response = JSON.parse(xhr.responseText);
            let productList = [];
            if (response.productCPUList && response.productCPUList.length > 0) {
                productList = productList.concat(createProductList(response.productCPUList));
            }
            if (response.productRAMList && response.productRAMList.length > 0) {
                productList = productList.concat(createProductList(response.productRAMList));
            }
            if (response.productVCList && response.productVCList.length > 0) {
                productList = productList.concat(createProductList(response.productVCList));
            }
            productList.forEach(product => {
                let productCell = createProductCell(product);
                productGrid.appendChild(productCell);
            });

            removeGridBorderTop();
            removeGridBorderBottom();
        }
    }
    // TODO: Fix search bar
    // if (searchQuery) {
    //     xhr.open("GET", `db_product_query.php?search=${searchQuery}`, true);
    // }
    // else {
    //     xhr.open("GET", "db_product_query.php", true);
    // }
    xhr.open("GET", "ProductListServlet", true);
    xhr.send();
}

function createProductList(jsonObject) {
    let productList = [];
    jsonObject.forEach(product => {
        let name, description;
        if (product.category === "cpu"){
            name = createProductName([product.brand, product.name]);
            description = {
                "Model": [product.model],
                "# of Cores": [product.numOfCores],
                "Frequency": [product.operatingFrequency, "GHz"],
                "Socket Type": [product.socketType]
            };
        }
        else if (product.category === "ram") {
            name = createProductName([product.brand, product.series]);
            description = {
                "Model": [product.model],
                "Capacity": [product.capacity],
                "Speed": [product.speed],
                "Timing": [product.timing],
                "Latency": [product.latency]
            };
        }
        else if(product.category === "videoCard") {
            name = createProductName([product.brand, product.series, product.gpu]);
            description = {
                "Model": [product.model],
                "Memory Size": [product.memorySize, "GB"],
                "Memory Type": [product.memoryType],
                "Max GPU Length": [product.maxGPULength, "mm"],
                "Dimensions": [product.cardDimensions]
            }
        }

        let imageSrc = "images/" + product.imgSrc;
        productList.push(new Product(product.id, product.category, name, description, product.price, imageSrc))
    })

    return productList;
}

function createProductName(attributeList) {
    let name = "";
    for (let i = 0; i < attributeList.length; i++) {
        name += ((attributeList[i] == null) ? "" : attributeList[i]);
        if (i < attributeList.length - 1){
            name += " ";
        }
    }

    return name;
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
    priceText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(product.price);
    priceText.classList.add('productcell__price');
    productCell.appendChild(priceText);

    let nameText = document.createElement('p');
    nameText.textContent = product.name;
    nameText.classList.add('productcell__name');
    productCell.appendChild(nameText);

    let descriptionList = document.createElement('ul');
    // key-value pair modeled as "LABEL": ["ATTRIBUTE", "UNITS"] (e.g. "Color": ["Black"], "Memory Size": ["11", "GB"])
    Object.keys(product.description).forEach(key => {
        if (!product.description[key].includes(null)) {
            let listItem = document.createElement('li');
            listItem.textContent = key + ": ";
            for (let i = 0; i < product.description[key].length; i++) {
                listItem.textContent += product.description[key][i];
                if (i < product.description[key].length - 1) {
                    listItem.textContent += " ";
                }
            }
            descriptionList.appendChild(listItem);
        }
    })
    descriptionList.classList.add('productcell__description');
    productCell.appendChild(descriptionList);

    productCell.classList.add('productcell');

    productCell.id = product.id;
    productCell.category = product.category;

    return productCell;
}

// TODO: Refactor to use servlets
function onProductClick(e) {
    let productCell = e.target.parentElement;
    let productQueryId = {id: productCell.id, category: productCell.category};
    sessionStorage.setItem('productQueryId', JSON.stringify(productQueryId));
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
    let numberOfRows = productGridComputedStyle.getPropertyValue("grid-template-rows").split(" ").length;
    let firstRow = productGrid.querySelectorAll('.productcell:nth-child(-n+' + numberOfColumns + ')');
    firstRow.forEach(productCell => {
        productCell.style.borderTop = "medium none transparent";
    });
    // Cells moving from first to second row get their border tops again
    if (numberOfRows > 1) {
        let secondRow = productGrid.querySelectorAll('.productcell:nth-child(n+' + (numberOfColumns + 1) + '):nth-child(-n+' + (2 * numberOfColumns) + ')');
        secondRow.forEach(productCell => {
            productCell.style.borderTop = "1px solid rgba(0, 62, 120, 0.1)";
        })
    }
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
    if (numberOfRows > 1) {
        let secondLastRow = productGrid.querySelectorAll('.productcell:nth-child(n+' + ((numberOfRows - 2) * numberOfColumns + 1) + '):nth-child(-n+' + ((numberOfRows - 1) * numberOfColumns) + ')');
        secondLastRow.forEach(productCell => {
            productCell.style.borderBottom = "1px solid rgba(0, 62, 120, 0.1)";
        })
    }
}

function clearProductGrid() {
    while (productGrid.hasChildNodes()) {
        productGrid.removeChild(productGrid.firstChild);
    }
}

