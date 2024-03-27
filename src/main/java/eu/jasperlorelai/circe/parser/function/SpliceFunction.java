package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class SpliceFunction extends Function {

	private ArrayNode array;
	private NumberLiteralNode index;
	private NumberLiteralNode deleteCount;
	private ArrayNode arrayAdd;

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
	public List<ParameterType> getParameterTypes() {
		return List.of(
				NodeType.NUMBER.asParameterType(),
				NodeType.NUMBER.asParameterType(),
				NodeType.ARRAY.asParameterType()
		);
	}

	@Override
	public void initializeArguments(List<ExpressionNode> arguments) {
		index = castNumber(arguments.get(0));
		deleteCount = castNumber(arguments.get(1));
		arrayAdd = castArray(arguments.get(2));
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		List<String> newArray = new ArrayList<>();
		List<ExpressionNode> list = array.array();
		int start = index.valueInteger();
		try {
			for (int i = 0; i < start; i++) {
				newArray.add(valueQuoteless(list.get(i)));
			}
			arrayAdd.array().forEach(item -> newArray.add(valueQuoteless(item)));
			for (int i = start + deleteCount.valueInteger(); i < list.size(); i++) {
				newArray.add(valueQuoteless(list.get(i)));
			}
		} catch (IndexOutOfBoundsException e) {
			throw formatException(e);
		}
		return arrayNode(newArray);
	}

}
