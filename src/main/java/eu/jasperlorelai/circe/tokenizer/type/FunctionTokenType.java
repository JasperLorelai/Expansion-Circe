package eu.jasperlorelai.circe.tokenizer.type;

import java.util.regex.Pattern;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;

import eu.jasperlorelai.circe.parser.function.*;
import eu.jasperlorelai.circe.parser.function.util.Function;
import eu.jasperlorelai.circe.tokenizer.type.util.TokenType;
import eu.jasperlorelai.circe.tokenizer.type.util.TokenSuperType;

public enum FunctionTokenType implements TokenType {

	// Special
	TO_STRING(ToStringFunction.class),
	TO_NUMBER(ToNumberFunction.class),
	TO_ARRAY(ToArrayFunction.class),
	ROUND(RoundFunction.class),
	CALC(CalcFunction.class),

	// Merged
	CONTAINS(ContainsFunction.class),
	EQUALS(EqualsFunction.class),
	INDEX_OF(IndexOfFunction.class),
	LAST_INDEX_OF(LastIndexOfFunction.class),
	SIZE(SizeFunction.class),
	IS_EMPTY(IsEmptyFunction.class),

	// String
	CHAR_AT(CharAtFunction.class),
	CHAR_CODE_AT(CharCodeAtFunction.class),
	ENDS_WITH(EndsWithFunction.class),
	LOWERCASE(LowercaseFunction.class),
	MATCH(MatchFunction.class),
	PAD_END(PadEndFunction.class),
	PAD_START(PadStartFunction.class),
	REPEAT(RepeatFunction.class),
	REPLACE_FIRST(ReplaceFirstFunction.class),
	REPLACE_ALL(ReplaceAllFunction.class),
	REVERSE_STRING(ReverseStringFunction.class),
	SPLIT(SplitFunction.class),
	STARTS_WITH(StartsWithFunction.class),
	SUBSTRING(SubstringFunction.class),
	TRIM(TrimFunction.class),
	UNICODE(UnicodeFunction.class),
	UPPERCASE(UppercaseFunction.class),

	// Array
	ELEMENT_AT(ElementAtFunction.class),
	JOIN(JoinFunction.class),
	REVERSE_ARRAY(ReverseArrayFunction.class),
	POP(PopFunction.class),
	PUSH(PushFunction.class),
	SHIFT(ShiftFunction.class),
	SLICE(SliceFunction.class),
	SPLICE(SpliceFunction.class),
	SUBTRACT(SubtractFunction.class),
	UNSHIFT(UnshiftFunction.class),

	;

	private final Class<? extends Function> functionClass;
	private final String functionName;
	private final Pattern pattern;

	FunctionTokenType(Class<? extends Function> functionClass) {
		this.functionClass = functionClass;
		String name = functionClass.getSimpleName();
		functionName = Character.toLowerCase(name.charAt(0)) + name.substring(1, name.lastIndexOf("Function"));
		pattern = createPattern(functionName);
	}

	public String functionName() {
		return functionName;
	}

	@NotNull
	@Override
	public Pattern regex() {
		return pattern;
	}

	@NotNull
	@Override
	public TokenSuperType type() {
		return TokenSuperType.FUNCTION;
	}

	public Function createFunction() {
		try {
			return functionClass.getDeclaredConstructor().newInstance();
		} catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

}
