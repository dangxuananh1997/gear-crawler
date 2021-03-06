let productType;
let lastPage;
let productXSL;
let timeoutSearch;

function getXSL() {
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      productXSL = this.responseXML;
    }
  };
  xhttp.open('GET', '/GearCrawler/xsl/product.xsl', true);
  xhttp.send();
}

function showLoading(isLoading) {
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
      
      let productListWrapper = document.getElementById('productList');
      // products not empty
      if (resLastPage !== 0) {
        if (lastPage !== resLastPage) {
          lastPage = resLastPage;
          updatePagination(lastPage);
        }
        showLoading(false);
        const result = transformXML(this.responseXML);
        productListWrapper.innerHTML = '';
        productListWrapper.append(result);
      } else {
        productListWrapper.innerHTML = '<h1 class="no-result">No Result!</h1>';
        updatePagination(0);
      }
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
    let xsltProcessor = new XSLTProcessor();
    xsltProcessor.importStylesheet(productXSL);
    let resultDocument = xsltProcessor.transformToFragment(xml, document);
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
  let selectedPage = document.querySelector('section.pagination nav a.selected');
  if (selectedPage) {
    selectedPage.classList.remove('selected');
  }
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
  let firstPage = document.querySelector('section.pagination nav a:first-child');
  if (firstPage) {
    firstPage.classList.add('selected');
  }
}