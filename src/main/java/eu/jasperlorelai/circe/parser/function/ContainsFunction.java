package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class ContainsFunction extends Function {

    private ArrayNode array;
    private StringLiteralNode string;
    private StringLiteralNode elementString;
    private NumberLiteralNode elementNumber;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return ParameterType.create(NodeType.STRING, NodeType.ARRAY);
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        array = castArray(target);
        string = castString(target);
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
        if (array == null) return stringFromBool(string.quoteless().contains(valueQuoteless(node)));
        else return stringFromBool(array.stringArray().contains(node.value()));
    }

}
