package eu.jasperlorelai.circe.parser.function.util;

import java.util.Map;
import java.util.HashMap;

import eu.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public class Variables {

    private static final Map<String, ExpressionNode> variables = new HashMap<>();

    public static Map<String, ExpressionNode> getVariables() {
        return variables;
    }

}
