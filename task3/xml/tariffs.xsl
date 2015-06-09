<?xml version="1.0"?>
<xsl:stylesheet version="1.0" 
  xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
  <xsl:template match="/">
    <html>
    <head>
    <title>List of all Tariffs</title>
      <xsl:apply-templates/>
    </head>
    </html>
  </xsl:template>
  <xsl:template match="tariffs">
    <body>
    <table border="1" cellpadding="5px">
	<tr><td colspan="12" style="text-align:center;"><b>List of all Tariffs</b></td></tr>
	<tr bgcolor="#d4d6e0">
	<td><b>Name</b></td>
	<td><b>TariffID</b></td>
	<td><b>OperatorName</b></td>
	<td><b>PayRoll</b></td>
	<td><b>CallPrices/InsideNetwork</b></td>
	<td><b>CallPrices/OutsideNetwork</b></td>
	<td><b>CallPrices/LandLinePhone</b></td>
	<td><b>SmsPrice</b></td>
	<td><b>Parameters/FavouriteNumber/Count</b></td>
	<td><b>Parameters/FavouriteNumber/Numbers</b></td>
	<td><b>Parameters/Tariffication</b></td>
	<td><b>Parameters/ConnectionCost</b></td>
	</tr>
	<xsl:apply-templates/>
    </table>
    </body>
  </xsl:template>
  <xsl:template match="tariff">

	<tr>
	<td><xsl:value-of select="name"/></td>
	<td><xsl:value-of select="name/@tariffID"/></td>
	<td><xsl:value-of select="operatorName"/></td>
	<td><xsl:value-of select="payroll"/></td>
	<td><xsl:value-of select="callPrices/insideNetwork"/></td>
	<td><xsl:value-of select="callPrices/outsideNetwork"/></td>
	<td><xsl:value-of select="callPrices/landLinePhone"/></td>
	<td><xsl:value-of select="smsPrice"/></td>
	<td><xsl:value-of select="parameters/favouriteNumber/@count"/></td>
	<td>
	<table>
	<xsl:for-each select="parameters/favouriteNumber/number">	
	<tr><td><xsl:value-of select="."/></td></tr>
	</xsl:for-each>
	</table>
	</td>
	<td><xsl:value-of select="parameters/tariffication"/></td>
	<td><xsl:value-of select="parameters/connectionCost"/></td>
	</tr>

  </xsl:template>
</xsl:stylesheet>


