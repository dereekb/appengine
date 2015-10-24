package com.dereekb.gae.server.search.document.query.builder.fields;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import com.google.appengine.api.search.Field.FieldType;

public class DocumentQueryNumberField extends DocumentQueryField {

	private static final String DOUBLE_FORMAT = "0.####E0";
	private static final String NUMBER_FIELD_FORMAT = "%s %s %s";

	private final Number value;
	private final String field;
	private final DocumentQueryOperator operator;

	public DocumentQueryNumberField(String field, Number value) {
		this(field, DocumentQueryOperator.Equal, value, false);
	}
	
	public DocumentQueryNumberField(String field, DocumentQueryOperator operator, Number value) {
		this(field, operator, value, false);
	}

	public DocumentQueryNumberField(String field, DocumentQueryOperator operator, Number value, Boolean not) {
		super(FieldType.ATOM, not);
		this.value = value;
		this.field = field;
		this.operator = operator;
	}

	@Override
	protected DocumentQueryField cloneQuery() {
		DocumentQueryNumberField clone = new DocumentQueryNumberField(this.field, this.operator,
				this.value, this.isNot());

		return clone;
	}

	@Override
	protected String getQueryString() {
		double d = this.value.doubleValue();
		
	    NumberFormat formatter = new DecimalFormat(DOUBLE_FORMAT);
	    String formattedDouble = formatter.format(d);
		String operatorString = this.operator.toString();
	     
	    String queryString = String.format(NUMBER_FIELD_FORMAT, this.field, operatorString, formattedDouble);
		return queryString;
	}
}
