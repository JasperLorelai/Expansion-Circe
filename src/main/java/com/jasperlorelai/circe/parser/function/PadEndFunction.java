package com.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.Function;
import com.jasperlorelai.circe.parser.expression.util.NodeType;
import com.jasperlorelai.circe.parser.function.util.ParameterType;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import com.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public class PadEndFunction extends Function {

    private StringLiteralNode string;
    private NumberLiteralNode minLength;
    private StringLiteralNode padChar;

    @Override
    public @NotNull List<ParameterType> getParameterTypes() {
        return List.of(
                NodeType.STRING.asParameterType(),
                NodeType.NUMBER.asParameterType(),
                NodeType.STRING.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        minLength = castNumber(arguments.get(1));
        padChar = castString(arguments.get(2));
    }

    @Override
    public @NotNull ExpressionNode execute() {
        StringBuilder newString = new StringBuilder(string.quoteless());
        while (minLength.valueInteger() > newString.length()) newString.append(padChar.quoteless());
        return stringNode(newString.toString());
    }

}
