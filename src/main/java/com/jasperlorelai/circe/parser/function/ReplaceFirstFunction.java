package com.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.regex.PatternSyntaxException;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class ReplaceFirstFunction extends Function {

    private StringLiteralNode string;
    private StringLiteralNode pattern;
    private StringLiteralNode replacement;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.STRING.asParameterType(),
            NodeType.STRING.asParameterType(),
            NodeType.STRING.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        pattern = castString(arguments.get(1));
        replacement = castString(arguments.get(2));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return stringNode(string.quoteless().replaceFirst(pattern.quoteless(), replacement.quoteless()));
        } catch (PatternSyntaxException e) {
            throw formatException(e);
        }
    }

}
