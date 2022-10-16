package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class CharCodeAtFunction extends Function {

    private StringLiteralNode string;
    private NumberLiteralNode index;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.STRING.asParameterType(),
            NodeType.NUMBER.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        index = castNumber(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return numberNode(string.quoteless().charAt(index.valueInteger()));
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
    }

}
