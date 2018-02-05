package com.dereekb.gae.extras.gen.utility.impl;

import java.util.Properties;

import com.dereekb.gae.extras.gen.utility.GenFile;
import com.dereekb.gae.utilities.data.ValueUtility;
import com.jamesmurty.utils.XMLBuilder2;

/**
 * {@link GenFile} for XML files from a {@link XMLBuilder2}.
 *
 * @author dereekb
 *
 */
public class XMLGenFileImpl extends AbstractGenFileImpl {

	public static final Properties DEFAULT_PROPERTIES = defaultProperties();
	public static final String XML_FILE_TYPE = "xml";

	private XMLBuilder2 result;
	private Properties outputProperties;

	public XMLGenFileImpl(String fileName, XMLBuilder2 result) {
		this(fileName, result, DEFAULT_PROPERTIES);
	}

	public XMLGenFileImpl(String fileName, XMLBuilder2 result, Properties outputProperties) {
		super(fileName, XML_FILE_TYPE);
		this.setResult(result);
		this.setOutputProperties(outputProperties);
	}

	public XMLBuilder2 getResult() {
		return this.result;
	}

	public void setResult(XMLBuilder2 result) {
		if (result == null) {
			throw new IllegalArgumentException("result cannot be null.");
		}

		this.result = result;
	}

	public Properties getOutputProperties() {
		return this.outputProperties;
	}

	public void setOutputProperties(Properties outputProperties) {
		this.outputProperties = outputProperties;
	}

	@Override
	public String getFileStringContents() throws UnsupportedOperationException {
		Properties properties = ValueUtility.defaultTo(this.outputProperties, DEFAULT_PROPERTIES);
		return this.result.asString(properties);
	}

	private static Properties defaultProperties() {
		Properties outputProperties = new Properties();

		// Explicitly identify the output as an XML document
		outputProperties.put(javax.xml.transform.OutputKeys.METHOD, "xml");

		// Pretty-print the XML output (doesn't work in all cases)
		outputProperties.put(javax.xml.transform.OutputKeys.INDENT, "yes");

		// Get 2-space indenting when using the Apache transformer
		outputProperties.put("{http://xml.apache.org/xslt}indent-amount", "2");

		// Omit the XML declaration header
		outputProperties.put(javax.xml.transform.OutputKeys.OMIT_XML_DECLARATION, "yes");

		return outputProperties;
	}

	@Override
	public String toString() {
		return "XMLGenFileImpl [result=" + this.result + ", outputProperties=" + this.outputProperties
		        + ", getFileName()=" + this.getFileName() + ", getFileType()=" + this.getFileType() + "]";
	}

}
