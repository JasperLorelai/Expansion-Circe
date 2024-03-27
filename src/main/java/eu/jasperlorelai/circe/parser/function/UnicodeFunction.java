package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class UnicodeFunction extends Function {

    private NumberLiteralNode code;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return NodeType.NUMBER.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        code = castNumber(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return stringNode(String.valueOf(Character.toChars(code.valueInteger())));
        } catch (IllegalArgumentException e) {
            throw formatException(e);
        }
    }

}
