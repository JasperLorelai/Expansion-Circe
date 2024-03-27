package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class IndexOfFunction extends Function {

	private StringLiteralNode string;
	private ArrayNode array;
	private StringLiteralNode elementString;
	private NumberLiteralNode elementNumber;

	@NotNull
	@Override
	public ParameterType getTargetType() {
		return ParameterType.create(NodeType.STRING, NodeType.ARRAY);
	}

	@Override
	public void initializeTarget(ExpressionNode target) {
		string = castString(target);
		array = castArray(target);
	}

	@NotNull
	@Override
	public List<ParameterType> getParameterTypes() {
		return List.of(ParameterType.create(NodeType.STRING, NodeType.NUMBER));
	}

	@Override
	public void initializeArguments(List<ExpressionNode> arguments) {
		elementString = castString(arguments.get(0));
		elementNumber = castNumber(arguments.get(0));
	}

	@NotNull
	@Override
	public ExpressionNode execute() {
		ExpressionNode node = elementString == null ? elementNumber : elementString;
		if (string == null) return numberNode(array.stringArray().indexOf(node.value()));
		else return numberNode(string.quoteless().indexOf(valueQuoteless(node)));
	}

}
