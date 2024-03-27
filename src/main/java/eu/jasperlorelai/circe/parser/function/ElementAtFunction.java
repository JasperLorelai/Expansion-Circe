package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class ElementAtFunction extends Function {

    private ArrayNode array;
    private NumberLiteralNode index;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.ARRAY.asParameterType(),
            NodeType.NUMBER.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        index = castNumber(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return array.array().get(index.valueInteger());
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
    }

}
