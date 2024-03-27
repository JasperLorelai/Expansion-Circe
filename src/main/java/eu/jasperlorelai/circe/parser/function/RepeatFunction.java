package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class RepeatFunction extends Function {

    private StringLiteralNode string;
    private NumberLiteralNode amount;

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
    public List<ParameterType> getParameterTypes() {
        return NodeType.NUMBER.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        amount = castNumber(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        try {
            return stringNode(string.quoteless().repeat(amount.valueInteger()));
        } catch (IllegalArgumentException e) {
            throw formatException(e);
        }
    }

}
