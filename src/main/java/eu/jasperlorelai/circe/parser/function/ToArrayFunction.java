package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.Collections;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class ToArrayFunction extends Function {

    private ExpressionNode node;

    @Override
    public @NotNull List<ParameterType> getParameterTypes() {
        // Any
        return List.of(ParameterType.create(NodeType.values()));
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        node = arguments.get(0);
    }

    @Override
    public @NotNull ExpressionNode execute() {
        return new ArrayNode(Collections.singletonList(node));
    }

}
