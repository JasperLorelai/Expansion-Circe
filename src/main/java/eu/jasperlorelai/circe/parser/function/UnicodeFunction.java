package eu.jasperlorelai.circe.parser.function;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class UnicodeFunction extends ZeroParamFunction {

    private NumberLiteralNode code;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return NodeType.NUMBER.asParameterType();
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        code = castNumber(target);
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
