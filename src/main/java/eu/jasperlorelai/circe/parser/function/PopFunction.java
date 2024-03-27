package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class PopFunction extends Function {

    private ArrayNode array;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return NodeType.ARRAY.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
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
