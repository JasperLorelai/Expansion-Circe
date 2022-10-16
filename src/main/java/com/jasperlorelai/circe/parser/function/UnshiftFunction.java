package com.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.ArrayNode;
import com.jasperlorelai.circe.parser.expression.StringLiteralNode;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class UnshiftFunction extends Function {

    private ArrayNode array;
    private StringLiteralNode elementString;
    private NumberLiteralNode elementNumber;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
            NodeType.ARRAY.asParameterType(),
            ParameterType.create(NodeType.STRING, NodeType.NUMBER)
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        elementString = castString(arguments.get(1));
        elementNumber = castNumber(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        List<String> list = new ArrayList<>();
        list.add(elementString == null ? elementNumber.value() : elementString.quoteless());
        array.array().forEach(item -> list.add(valueQuoteless(item)));
        return arrayNode(list);
    }

}
