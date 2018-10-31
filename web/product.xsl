<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : product.xsl
    Created on : October 31, 2018, 1:07 PM
    Author     : dangxuananh1997
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html" indent="yes"/>
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="products">
    <xsl:for-each select="product">
      <xsl:element name="a">
        <xsl:attribute name="href">
          <xsl:value-of select="productLink"/>
        </xsl:attribute>
        <article class="product">
          <div class="image-wrapper">
            <xsl:element name="img">
              <xsl:attribute name="src">
                <xsl:value-of select="image"/>
              </xsl:attribute>
            </xsl:element>
          </div>
          <div class="product-info">
            <p><xsl:value-of select="name"/></p>
            <span>
              <xsl:value-of select="price"/>
              <xsl:text>&#8363;</xsl:text>
            </span>
          </div>
        </article>
      </xsl:element>
    </xsl:for-each>
  </xsl:template>
</xsl:stylesheet>
