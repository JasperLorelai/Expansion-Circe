package eu.jasperlorelai.circe.parser.function;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class SizeFunction extends ZeroParamFunction {

	private ArrayNode array;
	private StringLiteralNode string;

	@NotNull
	@Override
	public ParameterType getTargetType() {
		return ParameterType.create(NodeType.ARRAY, NodeType.STRING);
	}

	@Override
	public void initializeTarget(ExpressionNode target) {
		array = castArray(target);
		string = castString(target);
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		return numberNode(array == null ? string.quoteless().length() : array.array().size());
	}

}
