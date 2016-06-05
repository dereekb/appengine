package com.dereekb.gae.test.server.search;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.dereekb.gae.model.general.geo.Point;
import com.dereekb.gae.server.search.document.query.expression.ExpressionOperator;
import com.dereekb.gae.server.search.document.query.expression.builder.ExpressionBuilder;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.AtomField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.DateField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.ExpressionWrap;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.GeoDistanceField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.NumberField;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.SimpleExpression;
import com.dereekb.gae.server.search.document.query.expression.builder.impl.field.TextField;

@RunWith(JUnit4.class)
public class DocumentQueryExpressionBuilderTests {

	private static final String TEST_FIELD_NAME = "field";

	@Test
	public void testAtomQueryField() {

		String value = "value";
		ExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

	}

	@Test
	public void testTextQueryField() {

		String value = "value";
		TextField builder = new TextField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

		builder.setStemming(true);

		expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

		builder.setSpecificText(true);

		expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);
	}

	@Test
	public void testNumberQueryField() {

		Number value = new Double(10);
		NumberField builder = new NumberField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

		builder.setValue(new Integer(10));
		expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

		builder.setOperator(ExpressionOperator.GreaterOrEqualTo);
		expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

	}

	@Test
	public void testDateQueryField() {

		Date value = new Date();
		DateField builder = new DateField(TEST_FIELD_NAME, value);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

	}

	@Test
	public void testGeoDistanceField() {

		Point point = new Point();
		Integer radius = 10;
		GeoDistanceField builder = new GeoDistanceField(TEST_FIELD_NAME, point, radius);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

	}

	@Test
	public void testExpressionWrap() {

		String value = "value";
		ExpressionBuilder builder = new AtomField(TEST_FIELD_NAME, value);
		ExpressionWrap wrap = new ExpressionWrap(builder);

		String expressionValue = wrap.getExpressionValue();
		Assert.assertNotNull(expressionValue);
	}

	@Test
	public void testSimpleExpressionQueryField() {

		String value = "hello world";
		SimpleExpression builder = new SimpleExpression(value);

		String expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);

		value = "complex OR expression";
		builder.setExpression(value);
		expressionValue = builder.getExpressionValue();
		Assert.assertNotNull(expressionValue);
		Assert.assertTrue(expressionValue.indexOf("OR") == -1);
	}


}