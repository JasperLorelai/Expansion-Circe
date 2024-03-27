package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class CharAtFunction extends Function {

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
            return stringNode(string.quoteless().charAt(index.valueInteger()) + "");
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
    }

}
