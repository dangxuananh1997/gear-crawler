let isCrawling = false;

let intervalCheck;

function checkIsCrawling() {
  let xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      console.log(this.responseXML);
      let resIsCrawling = this.responseXML.querySelector('isCrawling').innerHTML;
      if (resIsCrawling === 'false') {
        toggleIsCrawling(false);
        clearInterval(intervalCheck);
      }
    }
  };
  xhttp.open('GET', '/GearCrawler/getCrawlerStatus', true);
    xhttp.setRequestHeader("Cache-Control", "no-cache, no-store, must-revalidate");
  xhttp.send();
}

function crawl() {
  if (!isCrawling) {
    toggleIsCrawling(true);
    let xhttp = new XMLHttpRequest();
    xhttp.open('GET', '/GearCrawler/crawl?action=Crawl', true);
    xhttp.setRequestHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    xhttp.send();
    intervalCheck = setInterval(function() {
      checkIsCrawling();
    }, 2000);
  }
}

function pause() {
  if (isCrawling) {
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
      if (this.readyState === 4 && this.status === 200) {
        toggleIsCrawling(false);
      }
    };
    xhttp.open('GET', '/GearCrawler/crawl?action=Pause', true);
    xhttp.setRequestHeader("Cache-Control", "no-cache, no-store, must-revalidate");
    xhttp.send();
    clearInterval(intervalCheck);
  }
}

function toggleIsCrawling(bool) {
  let crawlButton = document.getElementById('crawl');
  let pauseButton = document.getElementById('pause');
  let spinner = document.getElementById('spinner');
  isCrawling = bool;
  if (isCrawling) {
    crawlButton.classList.add('disabled');
    pauseButton.classList.remove('disabled');
    spinner.classList.remove('hidden');
  } else {
    pauseButton.classList.add('disabled');
    crawlButton.classList.remove('disabled');
    spinner.classList.add('hidden');
  }
}