package com.jasperlorelai.circe.parser;

import java.util.List;
import java.util.ArrayList;

import com.jasperlorelai.circe.exeption.*;
import com.jasperlorelai.circe.tokenizer.type.*;
import com.jasperlorelai.circe.parser.expression.*;
import com.jasperlorelai.circe.tokenizer.Tokenizer;
import com.jasperlorelai.circe.tokenizer.Tokenizer.Token;
import com.jasperlorelai.circe.tokenizer.type.util.TokenType;
import com.jasperlorelai.circe.parser.function.util.Variables;
import com.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public class Parser {

    private Tokenizer tokenizer;
    private Token lookahead;

    private Parser() {}

    public static Parser create() {
        return new Parser();
    }

    private Token nextToken() {
        return (lookahead = tokenizer.getNextToken());
    }

    public String parse(String string) {
        tokenizer = Tokenizer.create();
        tokenizer.tokenize(string);
        // First token
        if (nextToken() == null) return "";

        Variables.getVariables().clear();
        ExpressionNode expression = expression();
        if (lookahead != null && lookahead.tokenType() != SpecialTokenType.COMMA_COLON) throw new ParserException("Expected end of input but found " + lookahead.tokenType() + ": '" + lookahead.value() + "'");

        if (expression == null) return "";

        /*
         * If we want the string node to be quoteless, we have to execute the function
         * expression manually, otherwise we'll get the right value() but of function node type.
         */
        if (expression instanceof FunctionNode functionNode) {
            expression = functionNode.getFunction().execute();
        }
        if (expression instanceof StringLiteralNode stringNode) return stringNode.quoteless();
        return expression.value();
    }

    private ExpressionNode expression() {
        if (lookahead == null) return null;
        return switch (lookahead.tokenType().type()) {
            case FUNCTION -> function();
            case LITERAL -> literal();
            case SPECIAL -> special();
        };
    }

    private ExpressionNode special() {
        if (lookahead == null) return null;
        TokenType tokenType = lookahead.tokenType();
        return switch ((SpecialTokenType) tokenType) {
            case DOLLAR -> variable();
            case BRACKET_OPEN -> array();
            default -> throw new ParserException("Expected expression, instead received: " + tokenType + " '" + lookahead.value() + "'");
        };
    }

    private ExpressionNode function() {
        if (lookahead == null) return null;
        String functionName = lookahead.value();
        FunctionNode node = new FunctionNode(lookahead);
        nextToken();
        // Open bracket.
        if (lookahead != null && lookahead.tokenType() != SpecialTokenType.PARENTHESES_OPEN) {
            throw new ParserException("Function '" + functionName + "' does not start with '('");
        }
        nextToken();

        // Collect function arguments.
        boolean closed = false;
        boolean awaitingArg = true;
        List<ExpressionNode> arguments = new ArrayList<>();
        while (lookahead != null) {
            // Check for closed bracket.
            if (lookahead.tokenType() == SpecialTokenType.PARENTHESES_CLOSE) {
                if (arguments.isEmpty()) awaitingArg = false;
                closed = true;
                nextToken();
                break;
            }

            // Check for argument of "expression" type.
            if (awaitingArg) {
                arguments.add(expression());
                awaitingArg = false;
                continue;
            }
            // Check for comma instead.
            if (lookahead.tokenType() == SpecialTokenType.COMMA) awaitingArg = true;
            else throw new ParserException("Unknown symbol " + lookahead.tokenType() + " in function '" + functionName + "': " + lookahead.value());
            nextToken();
        }

        if (awaitingArg) throw new ParserException("Expected argument after ',' in function '" + functionName + "'.");
        if (!closed) throw new ParserException("Function '" + functionName + "' does not end with ')'");
        node.process(arguments);
        return node;
    }

    private ExpressionNode literal() {
        if (lookahead == null) return null;
        String value = lookahead.value();
        ExpressionNode node = switch ((LiteralTokenType) lookahead.tokenType()) {
            case NUMBER -> new NumberLiteralNode(value);
            case STRING -> new StringLiteralNode(value);
        };
        nextToken();
        return node;
    }

    // Array follows JSON style.
    private ExpressionNode array() {
        List<ExpressionNode> list = new ArrayList<>();
        // Open bracket already processed.
        nextToken();

        // Collect elements in array.
        boolean closed = false;
        boolean awaitingItem = true;
        while (lookahead != null) {
            if (lookahead.tokenType() == SpecialTokenType.BRACKET_CLOSE) {
                if (list.isEmpty()) awaitingItem = false;
                closed = true;
                nextToken();
                break;
            }

            // Check for item of "expression" type.
            if (awaitingItem) {
                list.add(expression());
                awaitingItem = false;
                continue;
            }
            // Check for comma instead.
            if (lookahead.tokenType() == SpecialTokenType.COMMA) awaitingItem = true;
            else throw new ParserException("Unknown symbol " + lookahead.tokenType() + " + in array: " + lookahead.value());
            nextToken();
        }

        if (awaitingItem) throw new ParserException("Expected element after ',' in array.");
        if (!closed) throw new ParserException("Array does not end with ']'");
        return new ArrayNode(list);
    }

    private ExpressionNode variable() {
        // Collect the variable name.
        nextToken();
        if (lookahead == null || lookahead.tokenType() != SpecialTokenType.VARIABLE_NAME) throw new ParserException("Invalid variable reference/declaration.");
        String variableName = lookahead.value();
        nextToken();

        // Check for equals after var name. If not present, this is a variable reference.
        if (lookahead == null || lookahead.tokenType() != SpecialTokenType.EQUALS) {
            ExpressionNode node = Variables.getVariables().get(variableName);
            if (node == null) throw new ParserException("Invalid variable reference: $" + variableName);
            return node;
        }
        // Since the equals was present, this is variable declaration.
        if (Variables.getVariables().containsKey(variableName)) {
            throw new ParserException("Variable '" + variableName + "' has already been declared.");
        }

        // Collect expression to store.
        nextToken();
        ExpressionNode node = expression();
        if (node == null || lookahead == null || lookahead.tokenType() != SpecialTokenType.COMMA_COLON) {
            throw new ParserException("Invalid variable declaration: " + variableName);
        }
        node = node instanceof FunctionNode fun ? fun.getFunction().execute() : node;

        // Store.
        Variables.getVariables().put(variableName, node);
        nextToken();
        return expression();
    }

}
