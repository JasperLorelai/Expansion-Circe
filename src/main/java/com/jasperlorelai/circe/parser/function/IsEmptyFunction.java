package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.ArrayNode;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class IsEmptyFunction extends Function {

    private StringLiteralNode string;
    private ArrayNode array;

    @Override
    public @NotNull List<ParameterType> getParameterTypes() {
        return List.of(ParameterType.create(NodeType.STRING, NodeType.ARRAY));
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        array = castArray(arguments.get(0));
    }

    @Override
    public @NotNull ExpressionNode execute() {
        return stringFromBool(string == null ? array.array().isEmpty() : string.quoteless().isEmpty());
    }

}
