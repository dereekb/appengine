package com.dereekb.gae.test.server.search;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.dereekb.gae.server.search.query.expression.builder.SearchExpressionBuilder;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.DateField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.ExpressionWrap;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.NumberField;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.SimpleExpression;
import com.dereekb.gae.server.search.query.expression.builder.impl.field.TextField;
import com.dereekb.gae.server.search.query.utility.impl.SearchQueryExpressionSanitizerImpl;
import com.dereekb.gae.utilities.query.ExpressionOperator;

/**
 * Tests for the {@link SearchExpressionBuilder}.
 *
 * @author dereekb
 *
 */
public class SearchExpressionBuilderTests {

	private static final String TEST_FIELD_NAME = "field";

	@Test
	public void testAtomQueryField() {

		String value = "value";
		SearchExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

	}

	@Test
	public void testTextQueryField() {

		String value = "value";
		TextField builder = new TextField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

		builder.setStemming(true);

		expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

		builder.setSpecificText(true);

		expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);
	}

	@Test
	public void testNumberQueryField() {

		Number value = new Double(10);
		NumberField builder = new NumberField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

		builder.setValue(new Integer(10));
		expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

		builder.setOperator(ExpressionOperator.GREATER_OR_EQUAL_TO);
		expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

	}

	@Test
	public void testDateQueryField() {
		Date firstDate = new Date(0);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(firstDate);

		int dateYear = calendar.get(Calendar.YEAR);

		DateField builder = new DateField(TEST_FIELD_NAME, firstDate, ExpressionOperator.GREATER_THAN);
		int year = builder.getYear();

		assertTrue(year == dateYear, "The year should be set properly");

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);
	}

	@Test
	public void testGeoDistanceField() {

		PointImpl point = new PointImpl();
		Integer radius = 10;
		GeoDistanceField builder = new GeoDistanceField(TEST_FIELD_NAME, point, radius);

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

	}

	@Test
	public void testExpressionWrap() {

		String value = "value";
		SearchExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);
		ExpressionWrap wrap = new ExpressionWrap(builder);

		String expressionValue = wrap.getQueryExpression();
		assertNotNull(expressionValue);
	}

	@Test
	public void testSimpleExpressionQueryField() {

		String value = "hello world";
		SimpleExpression builder = new SimpleExpression(value);

		String expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);

		value = "complex OR expression";
		builder.setExpression(value);
		expressionValue = builder.getQueryExpression();
		assertNotNull(expressionValue);
		assertTrue(expressionValue.indexOf("OR") == -1);
	}

	@Test
	public void testSanitation() {
		SearchQueryExpressionSanitizerImpl sanitizer = new SearchQueryExpressionSanitizerImpl();

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
		String sanitizedNoCommaOrFunctionExpectedString = sanitizer
		        .sanitizeExpression(sanitizeNoCommaOrFunctionExpected);
		assertTrue(sanitizedNoCommaOrFunctionExpectedString.equals("a")); // Trims
		                                                                  // out
		                                                                  // extra
		                                                                  // floating
		                                                                  // ANDs
	}

}
