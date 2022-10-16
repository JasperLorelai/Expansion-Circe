package com.jasperlorelai.circe.parser.expression;

import java.util.List;
import java.util.ArrayList;

import org.jetbrains.annotations.NotNull;

import com.jasperlorelai.circe.parser.function.util.*;
import com.jasperlorelai.circe.exeption.ParserException;
import com.jasperlorelai.circe.parser.expression.util.*;
import com.jasperlorelai.circe.tokenizer.Tokenizer.Token;
import com.jasperlorelai.circe.tokenizer.type.FunctionTokenType;

public class FunctionNode implements ExpressionNode {

    private final String functionName;
    private final Function function;


    public FunctionNode(Token token) {
        FunctionTokenType type = (FunctionTokenType) token.tokenType();
        functionName = type.functionName();
        function = type.createFunction();
    }

    public void process(List<ExpressionNode> arguments) {
        // Convert all function nodes to other node types.
        List<ExpressionNode> localArgs = new ArrayList<>();
        for (ExpressionNode node : arguments) {
            if (node instanceof FunctionNode fun) localArgs.add(fun.getFunction().execute());
            else localArgs.add(node);
        }

        // Validate that argument type matches parameter type.
        List<ParameterType> paramTypes = function.getParameterTypes();
        if (localArgs.size() != paramTypes.size()) throw new ParserException("Function '" + functionName + "' expects " + paramTypes.size() + " arguments, but the supplied amount was " + localArgs.size());

        for (int i = 0; i < paramTypes.size(); i++) {
            ParameterType parameter = paramTypes.get(i);
            NodeType argument = localArgs.get(i).getType();
            if (parameter.isValid(argument)) continue;
            throw new ParserException("Function '" + functionName + "' has argument of " + argument + " type defined at position " + (i + 1) + " when " + parameter + " was expected.");
        }

        function.initializeArguments(localArgs);
    }

    @NotNull
    @Override
    public NodeType getType() {
        return NodeType.FUNCTION;
    }

    public Function getFunction() {
        return function;
    }

    @NotNull
    @Override
    public String value() {
        return getFunction().execute().value();
    }

}
