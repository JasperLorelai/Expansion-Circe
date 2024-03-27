package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class UnshiftFunction extends Function {

    private ArrayNode array;
    private StringLiteralNode elementString;
    private NumberLiteralNode elementNumber;

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
        List<String> list = new ArrayList<>();
        list.add(elementString == null ? elementNumber.value() : elementString.quoteless());
        array.array().forEach(item -> list.add(valueQuoteless(item)));
        return arrayNode(list);
    }

}
