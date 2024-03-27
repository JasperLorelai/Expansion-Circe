package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class EqualsFunction extends Function {

    private StringLiteralNode stringFirst;
    private StringLiteralNode stringSecond;
    private ArrayNode arrayFirst;
    private ArrayNode arraySecond;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return ParameterType.create(NodeType.STRING, NodeType.ARRAY);
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        stringFirst = castString(target);
        arrayFirst = castArray(target);
    }

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(ParameterType.create(NodeType.STRING, NodeType.ARRAY));
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        stringSecond = castString(arguments.get(0));
        arraySecond = castArray(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        if (arrayFirst != null && arraySecond != null) {
            return stringFromBool(arrayFirst.value().equals(arraySecond.value()));
        }
        if (stringFirst != null && stringSecond != null) {
            return stringFromBool(stringFirst.quoteless().equals(stringSecond.quoteless()));
        }
        return stringFromBool(false);
    }

}
