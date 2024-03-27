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
    public List<ParameterType> getParameterTypes() {
        ParameterType arrayOrString = ParameterType.create(NodeType.STRING, NodeType.ARRAY);
        return List.of(arrayOrString, arrayOrString);
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        stringFirst = castString(arguments.get(0));
        stringSecond = castString(arguments.get(1));
        arrayFirst = castArray(arguments.get(0));
        arraySecond = castArray(arguments.get(1));
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
