package eu.jasperlorelai.circe.tokenizer.type.util;

import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

public interface TokenType {

    @NotNull Pattern regex();

    @NotNull
    TokenSuperType type();

    default Pattern createPattern(String regex) {
        if (regex.charAt(0) != '^') regex = "^" + regex;
        return Pattern.compile(regex);
    }

}
