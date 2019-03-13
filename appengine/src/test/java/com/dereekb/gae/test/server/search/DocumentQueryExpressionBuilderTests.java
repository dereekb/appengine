package com.dereekb.gae.test.server.search;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.Test;

import com.dereekb.gae.model.general.geo.impl.PointImpl;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.DateField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionWrap;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.NumberField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.SimpleExpression;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.TextField;


public class DocumentQueryExpressionBuilderTests {

	private static final String TEST_FIELD_NAME = "field";

	@Test
	public void testAtomQueryField() {

		String value = "value";
		ExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

	}

	@Test
	public void testTextQueryField() {

		String value = "value";
		TextField builder = new TextField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

		builder.setStemming(true);

		expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

		builder.setSpecificText(true);

		expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);
	}

	@Test
	public void testNumberQueryField() {

		Number value = new Double(10);
		NumberField builder = new NumberField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

		builder.setValue(new Integer(10));
		expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

		builder.setOperator(ExpressionOperator.GREATER_OR_EQUAL_TO);
		expressionValue = builder.getExpressionValue();
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

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);
	}

	@Test
	public void testGeoDistanceField() {

		PointImpl point = new PointImpl();
		Integer radius = 10;
		GeoDistanceField builder = new GeoDistanceField(TEST_FIELD_NAME, point, radius);

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

	}

	@Test
	public void testExpressionWrap() {

		String value = "value";
		ExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);
		ExpressionWrap wrap = new ExpressionWrap(builder);

		String expressionValue = wrap.getExpressionValue();
		assertNotNull(expressionValue);
	}

	@Test
	public void testSimpleExpressionQueryField() {

		String value = "hello world";
		SimpleExpression builder = new SimpleExpression(value);

		String expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);

		value = "complex OR expression";
		builder.setExpression(value);
		expressionValue = builder.getExpressionValue();
		assertNotNull(expressionValue);
		assertTrue(expressionValue.indexOf("OR") == -1);
	}


}
