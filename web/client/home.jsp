<%-- 
    Document   : home
    Created on : Oct 31, 2018, 12:16:09 AM
    Author     : dangxuananh1997
--%>
<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gear Crawler - ${productType}</title>
    <link rel="stylesheet" href="css/client.css">
    <link rel="stylesheet" href="assets/fonts/material-icons/material-icons.css">
    <script>
      let productType = <s:property value="%{productType.getValue()}"/>;
    </script>
    <script src="js/client.js"></script>
  </head>
  <body>
    <header>
      <span>Gear Crawler</span>
      <nav>
        <a <s:if test="%{productType.getValue() == 1}">class="selected"</s:if> href="/GearCrawler/laptop">Laptop</a>
        <a <s:if test="%{productType.getValue() == 2}">class="selected"</s:if> href="/GearCrawler/mouse">Mouse</a>
        <a <s:if test="%{productType.getValue() == 3}">class="selected"</s:if> href="/GearCrawler/keyboard">Keyboard</a>
      </nav>
    </header>
        
    <main>
      <section class="search">
        <div>
          <i class="material-icons">search</i>
          <input type="text" name="search" id="search" onkeyup="search()">
          <button onclick="getData()">Search</button>
        </div>
      </section>
      
      <section class="product-list" id="productList">
        <c:import url="../product.xsl" var="xslProduct" />
        <s:set var="xml" value="%{xmlOutput}" />
        <x:transform xml="${xml}" xslt="${xslProduct}" />
      </section>

      <section class="pagination">
        <nav>
          <s:set var="lastPage" value="%{lastPage}" />
          <s:iterator begin="1" end="lastPage" status="i">
            <a data-page="<s:property value="%{#i.index + 1}"/>"
              class="<s:if test="%{0 == #i.index}">selected</s:if>"
              onclick="changePage(event)">
              <s:property value="%{#i.index + 1}"/></a>
          </s:iterator>
        </nav>
      </section>
    </main>

    <footer></footer>
  </body>
</html>
