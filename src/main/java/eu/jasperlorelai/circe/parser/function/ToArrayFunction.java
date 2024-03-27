package eu.jasperlorelai.circe.parser.function;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class ToArrayFunction extends ZeroParamFunction {

	private ExpressionNode node;

	@NotNull
	@Override
	public ParameterType getTargetType() {
		// Any
		return ParameterType.create(NodeType.values());
	}

	@Override
	public void initializeTarget(ExpressionNode target) {
		node = target;
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		return new ArrayNode(Collections.singletonList(node));
	}

}
