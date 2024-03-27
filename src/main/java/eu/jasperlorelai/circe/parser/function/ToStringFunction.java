package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class ToStringFunction extends Function {

	private NumberLiteralNode number;
	private NumberLiteralNode radix;

	@NotNull
	@Override
	public ParameterType getTargetType() {
		return NodeType.NUMBER.asParameterType();
	}

	@Override
	public void initializeTarget(ExpressionNode target) {
		number = castNumber(target);
	}

	@NotNull
	@Override
	public List<ParameterType> getParameterTypes() {
		return NodeType.NUMBER.asParameterTypes();
	}

	@Override
	public void initializeArguments(List<ExpressionNode> arguments) {
		radix = castNumber(arguments.get(0));
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		return stringNode(Integer.toString(number.valueInteger(), radix.valueInteger()));
	}

}
