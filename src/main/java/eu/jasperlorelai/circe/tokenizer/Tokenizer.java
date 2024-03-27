package eu.jasperlorelai.circe.tokenizer;

import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;

import eu.jasperlorelai.circe.tokenizer.type.*;
import eu.jasperlorelai.circe.tokenizer.type.util.*;
import eu.jasperlorelai.circe.exeption.UnknownTokenException;

public class Tokenizer {

	private static final List<TokenType> IGNORED_TOKENS = List.of(
			SpecialTokenType.WHITESPACE,
			SpecialTokenType.SINGLE_COMMENT,
			SpecialTokenType.MULTI_COMMENT
	);

	private List<Token> tokens;
	private int pointer;

	public record Token(TokenType tokenType, String value) {}

	private Tokenizer() {
		tokens = new ArrayList<>();
		pointer = 0;
	}

	public static Tokenizer create() {
		return new Tokenizer();
	}

	/**
	 * @throws UnknownTokenException if the string contains an unknown token
	 */
	public void tokenize(String string) {
		tokens = new ArrayList<>();

		while (!string.isEmpty()) {
			boolean hasMatch = false;
			for (TokenType tokenType : TokenTypeStore.getTypes()) {
				Matcher matcher = tokenType.regex().matcher(string);
				if (!matcher.find()) continue;
				hasMatch = true;

				String token = matcher.group().trim();
				if (!IGNORED_TOKENS.contains(tokenType)) {
					tokens.add(new Token(tokenType, token));
				}

				string = matcher.replaceFirst("");
				break;
			}
			if (hasMatch) continue;
			throw new UnknownTokenException("Unknown token: " + string);
		}
	}

	public boolean hasMoreTokens() {
		return pointer < tokens.size();
	}

	public Token getNextToken() {
		return hasMoreTokens() ? tokens.get(pointer++) : null;
	}

}
