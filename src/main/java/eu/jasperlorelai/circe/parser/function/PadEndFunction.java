package eu.jasperlorelai.circe.parser.function;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import eu.jasperlorelai.circe.parser.function.util.Function;
import eu.jasperlorelai.circe.parser.expression.util.NodeType;
import eu.jasperlorelai.circe.parser.function.util.ParameterType;
import eu.jasperlorelai.circe.parser.expression.StringLiteralNode;
import eu.jasperlorelai.circe.parser.expression.NumberLiteralNode;
import eu.jasperlorelai.circe.parser.expression.util.ExpressionNode;

public class PadEndFunction extends Function {

    private StringLiteralNode string;
    private NumberLiteralNode minLength;
    private StringLiteralNode padChar;

    @NotNull
    @Override
    public ParameterType getTargetType() {
        return NodeType.STRING.asParameterType();
    }

    @Override
    public void initializeTarget(ExpressionNode target) {
        string = castString(target);
    }

    @NotNull
    @Override
    public List<ParameterType> getParameterTypes() {
        return List.of(
                NodeType.NUMBER.asParameterType(),
                NodeType.STRING.asParameterType()
        );
    }

    @Override
    public void initializeArguments(List<ExpressionNode> arguments) {
        minLength = castNumber(arguments.get(0));
        padChar = castString(arguments.get(1));
    }

    @NotNull
    @Override
    public ExpressionNode execute() {
        StringBuilder newString = new StringBuilder(string.quoteless());
        while (minLength.valueInteger() > newString.length()) newString.append(padChar.quoteless());
        return stringNode(newString.toString());
    }

}
