package eu.jasperlorelai.circe.tokenizer.type;

import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import org.intellij.lang.annotations.Language;

import eu.jasperlorelai.circe.tokenizer.type.util.TokenType;
import eu.jasperlorelai.circe.tokenizer.type.util.TokenSuperType;

public enum LiteralTokenType implements TokenType {
    NUMBER("-?(\\d*\\.)?\\d+"),
    STRING("\"(?:[^\"\\\\]|\\\\.)*\""),
    ;

    private final Pattern pattern;

    LiteralTokenType(@Language("RegExp") String regex) {
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
        return TokenSuperType.LITERAL;
    }

}
