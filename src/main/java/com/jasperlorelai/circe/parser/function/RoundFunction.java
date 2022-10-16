package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class RoundFunction extends Function {

    private NumberLiteralNode number;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return NodeType.NUMBER.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        number = castNumber(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        return numberNode(number.valueInteger());
    }

}
