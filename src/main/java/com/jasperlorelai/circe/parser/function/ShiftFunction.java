package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.ArrayNode;

public class ShiftFunction extends Function {

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
        try {
            return array.array().get(0);
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
    }

}
