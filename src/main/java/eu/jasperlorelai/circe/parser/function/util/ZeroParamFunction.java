package eu.jasperlorelai.circe.parser.function.util;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public abstract class ZeroParamFunction extends Function {

	@NotNull
	@Override
	public final List<ParameterType> getParameterTypes() {
		return List.of();
	}

	@Override
	public final void initializeArguments(List<ExpressionNode> arguments) {}

}
