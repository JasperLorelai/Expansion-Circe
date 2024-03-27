package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class SubstringFunction extends Function {

    private StringLiteralNode string;
    private NumberLiteralNode startIndex;
    private NumberLiteralNode endIndex;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.STRING.asParameterType(),
            NodeType.NUMBER.asParameterType(),
            NodeType.NUMBER.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        string = castString(arguments.get(0));
        startIndex = castNumber(arguments.get(1));
        endIndex = castNumber(arguments.get(2));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return stringNode(string.quoteless().substring(startIndex.valueInteger(), endIndex.valueInteger()));
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
    }

}
