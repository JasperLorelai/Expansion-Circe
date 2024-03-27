package eu.jasperlorelai.circe.parser.function.util;

import java.util.Set;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

import eu.jasperlorelai.circe.parser.expression.util.NodeType;

public class ParameterType {

	private final Set<NodeType> validTypes = new HashSet<>();

	private ParameterType() {}

	public static ParameterType create(NodeType ...validTypes) {
		ParameterType type = new ParameterType();
		type.validTypes.addAll(Arrays.asList(validTypes));
		return type;
	}

	public boolean isValid(NodeType type) {
		return validTypes.contains(type);
	}

	@Override
	public String toString() {
		return validTypes.stream().map(Enum::name).collect(Collectors.joining(" or "));
	}

}
