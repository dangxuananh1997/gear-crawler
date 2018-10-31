let productXSL;

getXSL();

function getXSL() {
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      productXSL = this.responseXML;
    }
  };
  xhttp.open('GET', '/GearCrawler/product.xsl', true);
  xhttp.send();
}

function showLoading(isLoading) {
  console.log(isLoading);
  if (isLoading) {
  }
}

function getData(pageNumber) {
  showLoading(true);
  
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      showLoading(false);
      const result = transformXML(this.responseXML);
      document.getElementById("productList").innerHTML = "";
      document.getElementById("productList").append(result);
    }
  };
  
  const searchValue = document.getElementById('search').value;
  xhttp.open('GET', '/GearCrawler/getProduct?productType=' + productType + '&searchValue=' + searchValue + '&pageNumber=' + pageNumber, true);
  xhttp.send();
}

function transformXML(xml) {
  if (!productXSL) {
    getXSL();
  }
  if (document.implementation && document.implementation.createDocument) {
    xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(productXSL);
    resultDocument = xsltProcessor.transformToFragment(xml, document);
    return resultDocument;
  }
}

let timeoutSearch;

function search() {
  if (timeoutSearch) {
    clearTimeout(timeoutSearch);
  }
  timeoutSearch = setTimeout(function() {
    getData(1);
  }, 300);
}

function changePage(event) {
  const pageNumber = event.srcElement.dataset.page;
  getData(pageNumber);
  document.querySelector("section.pagination nav a.selected").classList.remove("selected");
  event.srcElement.classList.add('selected');
}