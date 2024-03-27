package eu.jasperlorelai.circe.parser.function;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;

public class IsEmptyFunction extends ZeroParamFunction {

    private StringLiteralNode string;
    private ArrayNode array;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return ParameterType.create(NodeType.STRING, NodeType.ARRAY);
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        string = castString(target);
        array = castArray(target);
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        return stringFromBool(string == null ? array.array().isEmpty() : string.quoteless().isEmpty());
    }

}
