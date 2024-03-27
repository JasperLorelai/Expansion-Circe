package eu.jasperlorelai.circe.tokenizer.type;

import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import org.intellij.lang.annotations.Language;

import eu.jasperlorelai.circe.tokenizer.type.util.*;

public enum SpecialTokenType implements TokenType {
    VARIABLE_NAME("[a-zA-Z_]\\w*"),
    PARENTHESES_OPEN("\\("),
    PARENTHESES_CLOSE("\\)"),
    BRACKET_OPEN("\\["),
    BRACKET_CLOSE("]"),
    DOLLAR("\\$"),
    EQUALS("="),
    COMMA(","),
    COMMA_COLON(";"),
    WHITESPACE("[\n\r\s]+"),
    SINGLE_COMMENT("//.*[\n\r]"),
    MULTI_COMMENT("/\\*(.|[\n\r])*\\*/"),
    ;

    private final Pattern pattern;

    SpecialTokenType(@Language("RegExp") String regex) {
        pattern = createPattern(regex);
    }

    @NotNull
    @Override
    public Pattern regex() {
        return pattern;
    }

    @NotNull
    @Override
    public TokenSuperType type() {
        return TokenSuperType.SPECIAL;
    }

}
