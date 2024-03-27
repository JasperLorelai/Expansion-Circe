package eu.jasperlorelai.circe.parser.function;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.parser.expression.ArrayNode;

public class SubtractFunction extends Function {

    private ArrayNode arrayFirst;
    private ArrayNode arraySecond;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return NodeType.ARRAY.asParameterType();
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        arrayFirst = castArray(target);
    }

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return NodeType.ARRAY.asParameterTypes();
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        arraySecond = castArray(arguments.get(0));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        List<String> list = new ArrayList<>();
        List<String> listSecond = arraySecond.stringArray();
        arrayFirst.array().forEach(item -> {
            if (listSecond.contains(item.value())) return;
            list.add(valueQuoteless(item));
        });
        return arrayNode(list);
    }

}
