package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class SliceFunction extends Function {

    private ArrayNode array;
    private NumberLiteralNode start;
    private NumberLiteralNode end;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
                NodeType.ARRAY.asParameterType(),
                NodeType.NUMBER.asParameterType(),
                NodeType.NUMBER.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        start = castNumber(arguments.get(1));
        end = castNumber(arguments.get(2));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        List<String> newArray = new ArrayList<>();
        List<ExpressionNode> list = array.array();
        for (int i = start.valueInteger(); i < Math.max(list.size() - 1, end.valueInteger()); i++) {
            try {
                newArray.add(valueQuoteless(list.get(i)));
            } catch (IndexOutOfBoundsException e) {
                throw formatException(e);
            }
        }
        return arrayNode(newArray);
    }

}
