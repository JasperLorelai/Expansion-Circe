package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.expression.*;
import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;

public class PushFunction extends Function {

    private ArrayNode array;
    private StringLiteralNode elementString;
    private NumberLiteralNode elementNumber;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.ARRAY.asParameterType(),
            ParameterType.create(NodeType.STRING, NodeType.NUMBER)
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        elementString = castString(arguments.get(1));
        elementNumber = castNumber(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        array.array().add(elementString == null ? elementNumber : elementString);
        return array;
    }

}
