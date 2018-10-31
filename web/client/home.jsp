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
    <link rel="stylesheet" href="css/style.css">
    <link rel="stylesheet" href="assets/fonts/material-icons/material-icons.css">
  </head>
  <body>
    <header>
      <span>Gear Crawler</span>
      <nav>
        <a <s:if test="%{productType.getValue() == 1}">class="selected"</s:if>href="/GearCrawler/laptop">Laptop</a>
        <a <s:if test="%{productType.getValue() == 2}">class="selected"</s:if> href="/GearCrawler/mouse">Mouse</a>
        <a <s:if test="%{productType.getValue() == 3}">class="selected"</s:if> href="/GearCrawler/keyboard">Keyboard</a>
      </nav>
    </header>
        
    <main>
      <section class="search">
        <form action="" method="POST">
          <i class="material-icons">search</i>
          <input type="text" name="search">
          <button type="submit">Search</button>
        </form>
      </section>
      
      <section class="product-list">
        <c:import url="../product.xsl" var="xslProduct" />
        <s:set var="xml" value="%{xmlOutput}" />
        <x:transform xml="${xml}" xslt="${xslProduct}" />
      </section>

      <section class="pagination">
        <nav>
          <s:set var="lastPage" value="%{lastPage}" />
          <s:iterator begin="1" end="lastPage" status="i">
            <a <s:if test="%{0 == #i.index}">class="selected"</s:if>><s:property /></a>
          </s:iterator>
        </nav>
      </section>
    </main>

    <footer></footer>
  </body>
</html>
