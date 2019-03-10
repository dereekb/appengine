package com.dereekb.gae.test.server.search.deprecated;

import static org.junit.assertFalse;
import static org.junit.assertNotNull;
import static org.junit.assertNull;
import static org.junit.assertTrue;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.dereekb.gae.server.search.document.query.deprecated.builder.DocumentQueryBuilder;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryDateField;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryField;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryGeoField;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryLiteralField;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryNumberField;
import com.dereekb.gae.server.search.document.query.deprecated.builder.fields.DocumentQueryTextField;
import com.dereekb.gae.server.search.document.query.impl.DocumentQueryExpressionSanitizerImpl;
import com.google.appengine.api.search.GeoPoint;

@RunWith(JUnit4.class)
public class DocumentQueryTest {

	private static final String TEST_FIELD_NAME = "field";

	@Test
	public void testDocumentQueryLiteralField() {

		String literalQuery = "name: aName";
		DocumentQueryLiteralField literalField = new DocumentQueryLiteralField(literalQuery);

		String literalToString = testDocumentQueryFieldTextResult(literalField);
		assertTrue(literalToString.equals(literalQuery));

		DocumentQueryField appendedLiteral = literalField.appendField(literalField);
		String appendedLiteralToString = appendedLiteral.toString();
		assertTrue(appendedLiteralToString.equals(literalQuery + " AND " + literalQuery));
	}

	@Test
	public void testTextQueryField() {

		String value = "value";
		DocumentQueryTextField textFieldQuery = new DocumentQueryTextField(TEST_FIELD_NAME, value);
		String textFieldQueryString = testDocumentQueryFieldTextResult(textFieldQuery);

		textFieldQuery.setStemming(true);
		String stemmedTextFieldQueryString = testDocumentQueryFieldTextResult(textFieldQuery);
		assertFalse(stemmedTextFieldQueryString.equals(textFieldQueryString));

		textFieldQuery.setSpecificText(true);
		String specificTextFieldQueryString = testDocumentQueryFieldTextResult(textFieldQuery);
		assertFalse(specificTextFieldQueryString.equals(textFieldQueryString));
	}

	@Test
	public void testDateQueryField() {

		Integer[] dates = { 2014, 1, 1 };
		DocumentQueryDateField dateFieldQuery = new DocumentQueryDateField(TEST_FIELD_NAME, dates);

		String dateFieldQueryString = testDocumentQueryFieldTextResult(dateFieldQuery);

		Integer year = 2014;
		Integer month = 1;
		Integer day = 1;
		DocumentQueryDateField alternateDateFieldQuery = new DocumentQueryDateField(TEST_FIELD_NAME, day, month, year);

		String alternateDateFieldQueryString = testDocumentQueryFieldTextResult(alternateDateFieldQuery);
		assertTrue(alternateDateFieldQueryString.equals(dateFieldQueryString));
	}

	@Test
	public void testNumberQueryField() {
		Double number = 100.0;
		DocumentQueryNumberField numberFieldQuery = new DocumentQueryNumberField(TEST_FIELD_NAME, number);
		testDocumentQueryFieldTextResult(numberFieldQuery);
	}

	@Test
	public void testQueryGeoField() {
		GeoPoint point = new GeoPoint(-20.0, 20.0);
		DocumentQueryGeoField geoFieldQuery = new DocumentQueryGeoField(TEST_FIELD_NAME, point);
		testDocumentQueryFieldTextResult(geoFieldQuery);

		Integer radius = 2222;
		DocumentQueryGeoField radiusGeoFieldQuery = new DocumentQueryGeoField(TEST_FIELD_NAME, point, radius);
		testDocumentQueryFieldTextResult(radiusGeoFieldQuery);
	}

	public String testDocumentQueryFieldTextResult(DocumentQueryField fieldQuery) {
		String queryString = fieldQuery.toString();
		assertNotNull(queryString);
		assertFalse(queryString.isEmpty());
		return queryString;
	}

	@Test
	public void testDocumentQueryToString() {

		String index = "index";

		String literalQuery = "test: test";
		DocumentQueryLiteralField literalField = new DocumentQueryLiteralField(literalQuery);
		DocumentQueryBuilder documentQuery = new DocumentQueryBuilder(index, literalField);

		String documentQueryToString = documentQuery.toString();
		assertNotNull(documentQueryToString);
		assertFalse(documentQueryToString.isEmpty());

		DocumentQueryBuilder appendedQuery = documentQuery.and(documentQuery);
		String appendedQueryToString = appendedQuery.toString();
		assertTrue(appendedQueryToString.equals("(" + literalQuery + ") AND ((" + literalQuery + "))"));

		DocumentQueryBuilder extendedAppendedQuery = appendedQuery.filter(literalField);
		String extendedAppendedQueryToString = extendedAppendedQuery.toString();
		String expectedFirstFieldQuery = literalQuery + " AND " + literalQuery;
		assertTrue(extendedAppendedQueryToString.equals("(" + expectedFirstFieldQuery + ") AND ((" + literalQuery
		        + "))"));
	}

	/**
	 * Tests building up a documentQuery. Tests the immutability of the DocumentQuery along with correctness.
	 */
	@Test
	public void testDocumentQueryExtending() {

		String index = "index";

		String testQueryField = "test: test";
		DocumentQueryBuilder documentQuery = new DocumentQueryBuilder(index);

		// Test Filter
		DocumentQueryLiteralField literalField = new DocumentQueryLiteralField(testQueryField);
		DocumentQueryBuilder filteredDocumentQuery = documentQuery.filter(literalField);

		// Check the old query to see that it has not changed.
		DocumentQueryField retrievedQueryFieldQuery = documentQuery.getFieldQuery();
		assertNull(retrievedQueryFieldQuery);

		// Check the new 'filtered' query that the literalField exists, but is also not the same object in memory as the
		// original literalField.
		DocumentQueryField retrievedFilteredQueryFieldQuery = filteredDocumentQuery.getFieldQuery();
		assertNotNull(retrievedFilteredQueryFieldQuery);
		assertTrue(retrievedFilteredQueryFieldQuery.toString().equals(literalField.toString()));
		assertFalse(retrievedFilteredQueryFieldQuery == literalField);

		// Test And
		DocumentQueryBuilder andQuery = documentQuery.and(documentQuery);
		assertNotNull(andQuery);
		assertFalse(andQuery == documentQuery);

		// Since the original has no filter, the resulting object should just be a clone of the filteredDocumentQuery.
		DocumentQueryBuilder andFilteredAndOriginalQuery = filteredDocumentQuery.and(documentQuery);
		assertNotNull(andFilteredAndOriginalQuery);

		String andFilteredOriginalString = andFilteredAndOriginalQuery.toString();
		String filteredDocumentQueryString = filteredDocumentQuery.toString();

		assertTrue(andFilteredOriginalString.equals(filteredDocumentQueryString));

		DocumentQueryBuilder andFilteredAndFilteredQuery = filteredDocumentQuery.and(filteredDocumentQuery);
		assertNotNull(andFilteredAndFilteredQuery);
		assertFalse(andFilteredAndFilteredQuery.isEmpty());

		// Test And and Filtered
		DocumentQueryBuilder extendedAndFilteredAndFilteredQuery = andFilteredAndFilteredQuery.filter(literalField);
		assertNotNull(extendedAndFilteredAndFilteredQuery);
		assertFalse(extendedAndFilteredAndFilteredQuery.isEmpty());
		assertTrue(extendedAndFilteredAndFilteredQuery.hasLink());

	}

	@Test
	public void testQueryIsComplex() {

		String index = "index";

		String testQueryField = "test: test";
		DocumentQueryLiteralField literalField = new DocumentQueryLiteralField(testQueryField);
		assertFalse(literalField.isComplex());

		DocumentQueryBuilder documentQuery = new DocumentQueryBuilder(index, literalField);

		// Test And
		DocumentQueryBuilder andQuery = documentQuery.and(documentQuery);
		assertFalse(andQuery.isComplex());

		// Test Or
		DocumentQueryBuilder orQuery = documentQuery.or(documentQuery);
		assertTrue(orQuery.isComplex());

		// Test Not
		DocumentQueryBuilder notQuery = documentQuery.not(documentQuery);
		assertTrue(notQuery.isComplex());

		// Test And, Or and Not
		DocumentQueryBuilder complexQuery = documentQuery.and(documentQuery).or(documentQuery).not(documentQuery);
		assertTrue(complexQuery.isComplex());

		DocumentQueryLiteralField complexLiteralField = new DocumentQueryLiteralField(testQueryField, true);
		DocumentQueryBuilder complexDocumentQuery = new DocumentQueryBuilder(index, complexLiteralField);
		assertTrue(complexDocumentQuery.isComplex());
	}

	@Test
	public void testSanitation() {
		DocumentQueryExpressionSanitizerImpl sanitizer = new DocumentQueryExpressionSanitizerImpl();

		sanitizer.setAllowAnd(true);
		sanitizer.setAllowComplex(true);

		String complexString = "beverage:wine color:(red OR white) NOT country:france a AND b NOT geopoint(x,y)";
		String sanitizeNothing = sanitizer.sanitizeExpression(complexString);
		assertTrue(complexString.equals(sanitizeNothing));

		sanitizer.setAllowFunctions(false);
		String sanitizeFunctions = sanitizer.sanitizeExpression(complexString);
		assertTrue(sanitizeFunctions.equals("beverage:wine color:(red OR white) NOT country:france a AND b"));

		sanitizer.setAllowFunctions(true);
		String comparisonAString = "model:gibson date < 1965-01-01";
		String comparisonASanitized = sanitizer.sanitizeExpression(comparisonAString);
		assertTrue(comparisonAString.equals(comparisonASanitized));

		String comparisonBString = "title:\"Harry Potter\" AND pages<500";

		String comparisonBSanitized = sanitizer.sanitizeExpression(comparisonBString);

		assertTrue(comparisonBString.equals(comparisonBSanitized));

		String sanitizeNotExpected = "a AND b";

		sanitizer.setAllowNot(false);

		String sanitizedNotString = sanitizer.sanitizeExpression(sanitizeNotExpected);

		assertTrue(sanitizeNotExpected.equals(sanitizedNotString));

		sanitizer.setAllowNot(true);
		sanitizer.setAllowFunctions(false);

		String sanitizeFloatingNot = "a AND b NOT";
		String sanitizedFloatingNotString = sanitizer.sanitizeExpression(sanitizeFloatingNot);
		assertTrue(sanitizedFloatingNotString.equals("a AND b"));

		sanitizer.setAllowFunctions(false);
		String sanitizeNoCommaOrFunctionExpected = "a AND , AND function(c,b)";
		String sanitizedNoCommaOrFunctionExpectedString = sanitizer.sanitizeExpression(sanitizeNoCommaOrFunctionExpected);
		assertTrue(sanitizedNoCommaOrFunctionExpectedString.equals("a")); // Trims out extra floating ANDs
	}
}
