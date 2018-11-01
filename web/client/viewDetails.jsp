<%-- 
    Document   : viewDetails
    Created on : Nov 1, 2018, 11:30:20 AM
    Author     : dangxuananh1997
--%>

<%@taglib uri="/struts-tags" prefix="s"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<s:set var="xml" value="%{xmlOutput}" />
<x:parse var="doc" xml="${xml}" />
<x:set var="price" select="number($doc//product/price)" />

<!DOCTYPE html>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Gear Crawler - <x:out select="$doc//product/name" /></title>
    <link rel="stylesheet" href="css/client.css">
    <link rel="stylesheet" href="assets/fonts/material-icons/material-icons.css">
  </head>
  <body>
    <header>
      <span>
        <a href="/GearCrawler">Gear Crawler</a>
      </span>
      <nav>
        <a href="/GearCrawler/laptop">Laptop</a>
        <a href="/GearCrawler/mouse">Mouse</a>
        <a href="/GearCrawler/keyboard">Keyboard</a>
      </nav>
    </header>
    
    <main>
      <section class="product-details">
        <div class="image-wrapper">
          <img src="<x:out select="$doc//product/image" />" alt="">
        </div>
        <div class="details-info">
          <h1><x:out select="$doc//product/name" /></h1>
          <h3><fmt:formatNumber type="number" maxFractionDigits="0" value="${price}" />&#8363;</h3>
          <x:choose>
            <x:when select = "$doc//product/productType='LAPTOP'">
              <c:import url="../xsl/laptop.xsl" var="xsl" />
            </x:when>
            <x:when select = "$doc//product/productType='MOUSE'">
              <c:import url="../xsl/mouse.xsl" var="xsl" />
            </x:when>
            <x:when select = "$doc//product/productType='KEYBOARD'">
              <c:import url="../xsl/keyboard.xsl" var="xsl" />
            </x:when>
            <x:otherwise />
          </x:choose>
          <x:transform xml="${xml}" xslt="${xsl}" />
        </div>
      </section>
    </main>
  </body>
</html>
