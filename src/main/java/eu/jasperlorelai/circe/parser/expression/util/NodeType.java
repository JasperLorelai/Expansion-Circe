package eu.jasperlorelai.circe.parser.expression.util;

import java.util.List;

import eu.jasperlorelai.circe.parser.function.util.ParameterType;

public enum NodeType {
    STRING,
    NUMBER,
    ARRAY,
    FUNCTION
    ;

    public ParameterType asParameterType() {
        return ParameterType.create(this);
    }

    public List<ParameterType> asParameterTypes() {
        return List.of(asParameterType());
    }

}
