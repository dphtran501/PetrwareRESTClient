const productImage = document.querySelector('#productimage');
const productPriceText = document.querySelector('#productprice');
const productNameText = document.querySelector('#productname');
const productModelText = document.querySelector('#productmodel');
const productDescriptionText = document.querySelector('#productdetails');
const productDescriptionTable = document.querySelector('#producttable');
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
    productDescriptionText.textContent = product.description;

    loadTable(product);
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

function loadTable(product) {
    
    if (product.brand) addTableRow("Brand", product.brand);
    if (product.name) addTableRow("Name", product.name);
    if (product.series) addTableRow("Series", product.series);
    if (product.model) addTableRow("Model", product.model);

    if (product.category == "cpu") {
        if (product.processorsType) addTableRow("Processors Type", product.processorsType);
        if (product.socketType) addTableRow("Socket Type", product.socketType);
        if (product.coreName) addTableRow("Core Name", product.coreName);
        if (product.numOfCores) addTableRow("# of Cores", product.numOfCores);
        if (product.numOfThreads) addTableRow("# of Threads", product.numOfThreads);
        if (product.operatingFrequency) addTableRow("Operating Frequency", product.operatingFrequency);
        if (product.maxTurboFrequency) addTableRow("Max Turbo Frequency", product.maxTurboFrequency);
    }
    else if (product.category == "ram") {
        if (product.capacity) addTableRow("Capacity", product.capacity);
        if (product.speed) addTableRow("Speed", product.speed);
        if (product.latency) addTableRow("Latency", product.latency);
        if (product.timing) addTableRow("Timing", product.timing);
        if (product.color) addTableRow("Color", product.color);
        if (product.colorLED) addTableRow("LED Color", product.colorLED);
    }
    else if (product.category == "videoCard") {
        if (product.interface) addTableRow("Interface", product.interface);
        if (product.chipset) addTableRow("Chipset", product.chipset);
        if (product.gpu) addTableRow("GPU", product.gpu);
        if (product.memorySize) addTableRow("Memory Size", product.memorySize);
        if (product.memoryType) addTableRow("Memory Type", product.memoryType);
        if (product.maxResolution) addTableRow("Max Resolution", product.maxResolution);
        if (product.cooler) addTableRow("Cooler", product.cooler);
        if (product.maxGPULength) addTableRow("Max GPU Length", product.maxGPULength);
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