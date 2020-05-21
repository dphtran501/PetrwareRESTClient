const productImage = document.querySelector('#product-image');
const productPriceText = document.querySelector('#product-price');
const productNameText = document.querySelector('#product-name');
const productModelText = document.querySelector('#product-model');
const productDescriptionText = document.querySelector('#product-details');
const productDescriptionTable = document.querySelector('#product-table');
const quantityInput = document.querySelector('#quantity-input');
const addButton = document.querySelector('#add-button');

window.addEventListener('load', onLoad);
addButton.addEventListener('click', onAddClick);

function onLoad() {
    let productQueryId = JSON.parse(sessionStorage.getItem('productQueryId'));
    var xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        // 4 means finished, and 200 means okay.
        if (xhr.readyState === 4 && xhr.status === 200) {
            //console.log(xhr.responseText);
            let response = JSON.parse(xhr.responseText);
            let product;
            if (response.productCPU) {
                product = response.productCPU;
            } else if (response.productRAM) {
                product = response.productRAM;
            } else if (response.productVC) {
                product = response.productVC;
            }

            productImage.src = "images/" + product.imgSrc;
            productPriceText.textContent = new Intl.NumberFormat('en-US', {style: 'currency', currency: 'USD'}).format(product.price);
            if (product.category === "cpu"){
                productNameText.textContent = createProductName([product.brand, product.name]);
            }
            else if (product.category === "ram") {
                productNameText.textContent = createProductName([product.brand, product.series]);
            }
            else if(product.category === "videoCard") {
                productNameText.textContent = createProductName([product.brand, product.series, product.gpu]);
            }
            productModelText.textContent = "Model: " + product.model;
            productDescriptionText.textContent = product.description;

            loadTable(product);

        }
    }
    xhr.open("GET", `ProductServlet?id=${productQueryId.id}&category=${productQueryId.category}`, true);
    xhr.send();

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

// TODO: refactor to use servlets
function onAddClick() {
    if (Number(quantityInput.value) > 0) {
        let pID = JSON.parse(sessionStorage.getItem('productQueryId'));
        let quantity = quantityInput.value;
        var xhr = new XMLHttpRequest();
        xhr.onreadystatechange = function() {
            if (xhr.readyState === 4 && xhr.status === 200) {
                window.open('checkout.html', '_self');
            }
        }
        xhr.open("POST", "CartServlet/add", true);
        xhr.setRequestHeader("content-type", "application/x-www-form-urlencoded");
        xhr.send(`pID=${pID.id}&quantity=${quantity}`);
    }
}

function loadTable(product) {
    
    if (product.brand) addTableRow("Brand", product.brand);
    if (product.name) addTableRow("Name", product.name);
    if (product.series) addTableRow("Series", product.series);
    if (product.model) addTableRow("Model", product.model);

    if (product.category === "cpu") {
        if (product.processorsType) addTableRow("Processors Type", product.processorsType);
        if (product.socketType) addTableRow("Socket Type", product.socketType);
        if (product.coreName) addTableRow("Core Name", product.coreName);
        if (product.numOfCores) addTableRow("# of Cores", product.numOfCores);
        if (product.numOfThreads) addTableRow("# of Threads", product.numOfThreads);
        if (product.operatingFrequency) addTableRow("Operating Frequency", product.operatingFrequency + " GHz");
        if (product.maxTurboFrequency) addTableRow("Max Turbo Frequency", product.maxTurboFrequency + " GHz");
    }
    else if (product.category === "ram") {
        if (product.capacity) addTableRow("Capacity", product.capacity);
        if (product.speed) addTableRow("Speed", product.speed);
        if (product.latency) addTableRow("Latency", product.latency);
        if (product.timing) addTableRow("Timing", product.timing);
        if (product.color) addTableRow("Color", product.color);
        if (product.colorLED) addTableRow("LED Color", product.colorLED);
    }
    else if (product.category === "videoCard") {
        if (product.interface) addTableRow("Interface", product.interface);
        if (product.chipset) addTableRow("Chipset", product.chipset);
        if (product.gpu) addTableRow("GPU", product.gpu);
        if (product.memorySize) addTableRow("Memory Size", product.memorySize + " GB");
        if (product.memoryType) addTableRow("Memory Type", product.memoryType);
        if (product.maxResolution) addTableRow("Max Resolution", product.maxResolution);
        if (product.cooler) addTableRow("Cooler", product.cooler);
        if (product.maxGPULength) addTableRow("Max GPU Length", product.maxGPULength + " mm");
        if (product.cardDimensions) addTableRow("Card Dimensions", product.cardDimensions);
    }
}

function addTableRow(fieldLabel, fieldValue) {
    let newRow = productDescriptionTable.insertRow(-1);
    let labelCell = newRow.insertCell(0);
    let valueCell = newRow.insertCell(1);

    labelCell.appendChild(document.createTextNode(fieldLabel));
    valueCell.appendChild(document.createTextNode(fieldValue));
}