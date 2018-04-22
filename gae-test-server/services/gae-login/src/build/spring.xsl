<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:xs="http://www.w3.org/2001/XMLSchema"
	xmlns:fn="http://www.w3.org/2005/xpath-functions">
	<xsl:output method="xml" omit-xml-declaration="yes" indent="yes" />

	<!-- Match the root node and apply templates to the copy? -->
	<xsl:template match="/">
		<!-- Main Root Copy -->
		<xsl:copy>
			<xsl:apply-templates mode="rootcopy" />
		</xsl:copy>
	</xsl:template>

	<xsl:template match="node()" mode="rootcopy">
		<xsl:copy>
			<!-- Copy all attributes -->
    		<xsl:copy-of select="@*"/>
    		<!-- Import all remaining components from sub directories. -->
			<xsl:variable name="folderURI" select="resolve-uri('.',base-uri())" />
			<xsl:for-each
				select="collection(concat($folderURI, '?select=*.xml;recurse=yes'))/*/node()">
				<!-- Import the elements for each and copy all internal nodes. -->
				<xsl:apply-templates mode="copy" select="." />
			</xsl:for-each>
		</xsl:copy>
	</xsl:template>

	<!-- Deep copy template -->
	<xsl:template match="node()|@*" mode="copy">
		<!-- Executed for each element, except imports -->
		<xsl:if test="name() != 'import'">
			<xsl:copy>
				<!-- Copy Attributes -->
				<xsl:apply-templates mode="copy" select="@*" />
				<!-- Copy Children Elements -->
				<xsl:apply-templates mode="copy" />
			</xsl:copy>
		</xsl:if>
	</xsl:template>
	
	<!-- Catch All Remaining -->
	<xsl:template match="*" />

</xsl:stylesheet>