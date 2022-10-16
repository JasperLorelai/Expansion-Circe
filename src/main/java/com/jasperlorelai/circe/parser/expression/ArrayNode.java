package com.jasperlorelai.circe.parser.expression;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.expression.util.NodeType;
import com.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public record ArrayNode(List<ExpressionNode> array) implements ExpressionNode {

    public ArrayNode(List<ExpressionNode> array) {
        List<ExpressionNode> temp = new ArrayList<>();
        array.forEach(item -> {
            if (item instanceof FunctionNode fun) temp.add(fun.getFunction().execute());
            else temp.add(item);
        });
        this.array = temp;
    }

    @NotNull
    @Override
    public NodeType getType() {
        return NodeType.ARRAY;
    }

    public List<String> stringArray() {
        return array.stream().map(ExpressionNode::value).toList();
    }

    @NotNull
    @Override
    public String value() {
        return "[" + String.join(", ", stringArray()) + "]";
    }

}
