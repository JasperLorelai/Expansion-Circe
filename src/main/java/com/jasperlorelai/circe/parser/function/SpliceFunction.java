package com.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.parser.expression.ArrayNode;
import com.jasperlorelai.circe.parser.expression.NumberLiteralNode;

public class SpliceFunction extends Function {

    private ArrayNode array;
    private NumberLiteralNode index;
    private NumberLiteralNode deleteCount;
    private ArrayNode arrayAdd;

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
                NodeType.ARRAY.asParameterType(),
                NodeType.NUMBER.asParameterType(),
                NodeType.NUMBER.asParameterType(),
                NodeType.ARRAY.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        array = castArray(arguments.get(0));
        index = castNumber(arguments.get(1));
        deleteCount = castNumber(arguments.get(2));
        arrayAdd = castArray(arguments.get(3));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        List<String> newArray = new ArrayList<>();
        List<ExpressionNode> list = array.array();
        int start = index.valueInteger();
        try {
            for (int i = 0; i < start; i++) {
                newArray.add(valueQuoteless(list.get(i)));
            }
            arrayAdd.array().forEach(item -> newArray.add(valueQuoteless(item)));
            for (int i = start + deleteCount.valueInteger(); i < list.size(); i++) {
                newArray.add(valueQuoteless(list.get(i)));
            }
        } catch (IndexOutOfBoundsException e) {
            throw formatException(e);
        }
        return arrayNode(newArray);
    }

}
