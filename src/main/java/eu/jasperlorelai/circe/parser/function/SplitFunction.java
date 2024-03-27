package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.Arrays;
import java.util.regex.PatternSyntaxException;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class SplitFunction extends Function {

    private StringLiteralNode string;
    private StringLiteralNode pattern;
    private NumberLiteralNode limit;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.STRING.asParameterType(),
            NodeType.STRING.asParameterType(),
                NodeType.NUMBER.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        pattern = castString(arguments.get(1));
        limit = castNumber(arguments.get(2));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return arrayNode(Arrays.asList(string.quoteless().split(pattern.quoteless(), limit.valueInteger())));
        } catch (PatternSyntaxException e) {
            throw formatException(e);
        }
    }

}
