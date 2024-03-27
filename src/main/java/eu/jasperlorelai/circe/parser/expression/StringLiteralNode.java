package eu.jasperlorelai.circe.parser.expression;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.expression.util.*;

public record StringLiteralNode(String value) implements ExpressionNode {

    @NotNull
    @Override
    public NodeType getType() {
        return NodeType.STRING;
    }

    public String quoteless() {
        return value.substring(1, value.length() - 1).replaceAll("\\\\\"", "\"");
    }

    @Override
    @NotNull
    public String value() {
        return value;
    }

}
