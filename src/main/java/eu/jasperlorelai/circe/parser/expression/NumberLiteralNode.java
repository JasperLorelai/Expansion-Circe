package eu.jasperlorelai.circe.parser.expression;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.expression.util.*;

public class NumberLiteralNode implements ExpressionNode {

	private final String valueString;
	private final double valueDouble;
	private final int valueInteger;

	public NumberLiteralNode(String value) {
		valueString = value;
		valueDouble = Double.parseDouble(value);
		valueInteger = (int) Math.round(valueDouble);
	}

	@NotNull
	@Override
	public NodeType getType() {
		return NodeType.NUMBER;
	}

	@NotNull
	public String value() {
		return valueString;
	}

	public double valueDouble() {
		return valueDouble;
	}

	public int valueInteger() {
		return valueInteger;
	}

}
