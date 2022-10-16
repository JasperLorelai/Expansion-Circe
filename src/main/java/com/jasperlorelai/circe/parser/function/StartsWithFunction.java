package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class StartsWithFunction extends Function {

    private StringLiteralNode string;
    private StringLiteralNode substring;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.STRING.asParameterType(),
            NodeType.STRING.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        substring = castString(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        return stringFromBool(string.quoteless().startsWith(substring.quoteless()));
    }

}
