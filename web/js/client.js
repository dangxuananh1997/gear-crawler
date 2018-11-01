let productXSL;
let timeoutSearch;

// document ready
document.addEventListener("DOMContentLoaded", function() { 
  updatePagination(lastPage);
  getXSL();
});

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
      let resLastPage = this.responseXML.querySelector('lastPage').innerHTML;
      resLastPage = parseInt(resLastPage);
      if (lastPage !== resLastPage) {
        lastPage = resLastPage;
        updatePagination(lastPage);
      }
      showLoading(false);
      const result = transformXML(this.responseXML);
      document.getElementById('productList').innerHTML = '';
      document.getElementById('productList').append(result);
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
  document.querySelector('section.pagination nav a.selected').classList.remove('selected');
  event.srcElement.classList.add('selected');
}

function updatePagination(lastPage) {
  const paginationDiv = document.querySelector('section.pagination nav');
  let pages = '';
  let i;
  for (i = 1; i <= lastPage; i++) {
    pages += '<a data-page="' + i + '" onclick="changePage(event)">' + i + '</a>';
  }
  paginationDiv.innerHTML = pages;
  document.querySelector('section.pagination nav a:first-child').classList.add('selected');
}