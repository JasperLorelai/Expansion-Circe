package com.jasperlorelai.circe.parser.expression.util;

import org.jetbrains.annotations.NotNull;

public interface ExpressionNode {

    @NotNull
    NodeType getType();

    @NotNull
    String value();

}
