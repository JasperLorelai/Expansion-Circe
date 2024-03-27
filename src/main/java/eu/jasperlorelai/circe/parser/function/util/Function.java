package eu.jasperlorelai.circe.parser.function.util;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.expression.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.exeption.FunctionCallException;

/**
 * Used to connect all function classes together.
 */
public abstract class Function {

    private final String functionName;

    public Function() {
        String name = getClass().getSimpleName();
        functionName = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.lastIndexOf("Function"));
    }

    @NotNull
    public abstract List<ParameterType> getParameterTypes();

    public abstract void initializeArguments(List<ExpressionNode> arguments);

    @NotNull
    public abstract ExpressionNode execute();

    protected FunctionCallException formatException(RuntimeException exception) {
        return new FunctionCallException(functionName + ": \"" + exception.getMessage() + '"');
    }

    protected StringLiteralNode castString(ExpressionNode node) {
        return node instanceof StringLiteralNode string ? string : null;
    }

    protected NumberLiteralNode castNumber(ExpressionNode node) {
        return node instanceof NumberLiteralNode number ? number : null;
    }

    protected ArrayNode castArray(ExpressionNode node) {
        return node instanceof ArrayNode array ? array : null;
    }

    protected static String valueQuoteless(ExpressionNode node) {
        return node instanceof StringLiteralNode string ? string.quoteless() : node.value();
    }

    protected static String quote(String string) {
        return '"' + string + '"';
    }

    protected static StringLiteralNode stringNode(String value) {
        return new StringLiteralNode(quote(value));
    }

    protected static NumberLiteralNode numberNode(int value) {
        return new NumberLiteralNode(value + "");
    }

    protected static StringLiteralNode stringFromBool(boolean bool) {
        return stringNode(Boolean.toString(bool));
    }

    protected static ArrayNode arrayNode(List<String> list) {
        List<ExpressionNode> newList = new ArrayList<>();
        list.forEach(item -> newList.add(new StringLiteralNode(quote(item))));
        return new ArrayNode(newList);
    }

}
