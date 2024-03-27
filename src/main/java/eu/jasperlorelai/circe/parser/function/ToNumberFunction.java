package eu.jasperlorelai.circe.parser.function;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class ToNumberFunction extends ZeroParamFunction {

    private StringLiteralNode string;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return NodeType.STRING.asParameterType();
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        string = castString(target);
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
