package eu.jasperlorelai.circe.parser.function;

import java.util.Map;
import java.util.HashMap;

import org.jetbrains.annotations.NotNull;

import net.objecthunter.exp4j.ExpressionBuilder;

import eu.jasperlorelai.circe.parser.expression.*;
import eu.jasperlorelai.circe.parser.function.util.*;
import eu.jasperlorelai.circe.parser.expression.util.*;
import eu.jasperlorelai.circe.exeption.FunctionCallException;

public class CalcFunction extends ZeroParamFunction {

	private StringLiteralNode string;

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
	public ExpressionNode execute() {
		try {
			Map<String, Double> variableMap = new HashMap<>();
			Variables.getVariables().forEach((name, value) -> {
				if (!(value instanceof NumberLiteralNode number)) return;
				variableMap.put(name, number.valueDouble());
			});

			return new NumberLiteralNode(new ExpressionBuilder(string.quoteless())
					.variables(variableMap.keySet())
					.build()
					.setVariables(variableMap)
					.evaluate() + ""
			);
		} catch (IllegalArgumentException | FunctionCallException e) {
			throw formatException(e);
		}
	}

}
