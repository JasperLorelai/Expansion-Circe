package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class ToNumberFunction extends Function {

    private StringLiteralNode string;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return NodeType.STRING.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return new NumberLiteralNode(Double.parseDouble(string.quoteless()) + "");
        } catch (NumberFormatException e) {
            throw formatException(e);
        }
    }

}
