package eu.jasperlorelai.circe.parser.function;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class RoundFunction extends ZeroParamFunction {

    private NumberLiteralNode number;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return NodeType.NUMBER.asParameterType();
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        number = castNumber(target);
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        return numberNode(number.valueInteger());
    }

}
