package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class PopFunction extends ZeroParamFunction {

	private ArrayNode array;

	@NotNull
	@Override
	public ParameterType getTargetType() {
		return NodeType.ARRAY.asParameterType();
	}

	@Override
	public void initializeTarget(ExpressionNode target) {
		array = castArray(target);
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		List<ExpressionNode> list = array.array();
		try {
			return list.get(list.size() - 1);
		} catch (IndexOutOfBoundsException e) {
			throw formatException(e);
		}
	}

}
