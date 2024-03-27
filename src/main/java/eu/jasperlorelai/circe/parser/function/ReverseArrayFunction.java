package eu.jasperlorelai.circe.parser.function;

import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class ReverseArrayFunction extends ZeroParamFunction {

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
        Collections.reverse(array.array());
        return array;
    }

}
