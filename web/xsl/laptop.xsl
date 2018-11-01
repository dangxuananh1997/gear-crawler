<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : laptop.xsl
    Created on : November 1, 2018, 12:40 PM
    Author     : dangxuananh1997
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
  <xsl:output method="html"/>
  <xsl:template match="/">
    <xsl:apply-templates/>
  </xsl:template>
  <xsl:template match="mouse">
    <table>
      <tr>
        <th>CPU</th>
        <td><xsl:value-of select="cpu"/></td>
      </tr>
      <tr>
        <th>GPU</th>
        <td><xsl:value-of select="gpu"/></td>
      </tr>
      <tr>
        <th>RAM</th>
        <td><xsl:value-of select="ram"/></td>
      </tr>
      <tr>
        <th>Hard Drive</th>
        <td><xsl:value-of select="hardDrive"/></td>
      </tr>
      <tr>
        <th>Monitor</th>
        <td><xsl:value-of select="monitor"/></td>
      </tr>
      <tr>
        <th>Ports</th>
        <td><xsl:value-of select="ports"/></td>
      </tr>
      <tr>
        <th>Lan</th>
        <td><xsl:value-of select="lan"/></td>
      </tr>
      <tr>
        <th>Wireless</th>
        <td><xsl:value-of select="wireless"/></td>
      </tr>
      <tr>
        <th>Link</th>
        <td>
          <xsl:element name="a">
            <xsl:attribute name="href">
              <xsl:text><xsl:value-of select="product/productLink"/></xsl:text>
            </xsl:attribute>
            <xsl:value-of select="product/productLink"/>
          </xsl:element>
        </td>
      </tr>
    </table>
  </xsl:template>
  <xsl:template match="product" />
</xsl:stylesheet>
