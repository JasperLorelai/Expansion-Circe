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
    public List<ParameterType> getParameterTypes() {
        return List.of(
                NodeType.ARRAY.asParameterType(),
                NodeType.ARRAY.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        arrayFirst = castArray(arguments.get(0));
        arraySecond = castArray(arguments.get(1));
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
