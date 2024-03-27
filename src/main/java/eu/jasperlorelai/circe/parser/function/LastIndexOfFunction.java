package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class LastIndexOfFunction extends Function {

    private StringLiteralNode string;
    private ArrayNode array;
    private StringLiteralNode elementString;
    private NumberLiteralNode elementNumber;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
                ParameterType.create(NodeType.STRING, NodeType.ARRAY),
                ParameterType.create(NodeType.STRING, NodeType.NUMBER)
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        array = castArray(arguments.get(0));
        elementString = castString(arguments.get(1));
        elementNumber = castNumber(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        ExpressionNode node = elementString == null ? elementNumber : elementString;
        if (string == null) return numberNode(array.stringArray().lastIndexOf(node.value()));
        else return numberNode(string.quoteless().lastIndexOf(valueQuoteless(node)));
    }

}
