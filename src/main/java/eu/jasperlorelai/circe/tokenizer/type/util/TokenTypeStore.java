package eu.jasperlorelai.circe.tokenizer.type.util;

import java.util.List;
import java.util.ArrayList;

import eu.jasperlorelai.circe.tokenizer.type.*;

public class TokenTypeStore {

	private static final List<TokenType> types = new ArrayList<>();
	static {
		add(FunctionTokenType.values());
		add(LiteralTokenType.values());
		add(SpecialTokenType.values());
	}

	private static void add(TokenType[] types) {
		getTypes().addAll(List.of(types));
	}

	public static List<TokenType> getTypes() {
		return types;
	}

}
