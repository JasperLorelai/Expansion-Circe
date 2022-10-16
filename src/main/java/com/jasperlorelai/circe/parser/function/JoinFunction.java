package com.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.ArrayNode;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class JoinFunction extends Function {

    private ArrayNode array;
    private StringLiteralNode delimiter;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.ARRAY.asParameterType(),
            NodeType.STRING.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        delimiter = castString(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        return stringNode(array.array()
                .stream()
                .map(Function::valueQuoteless)
                .collect(Collectors.joining(delimiter.quoteless()))
        );
    }

}
