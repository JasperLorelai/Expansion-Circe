package com.jasperlorelai.circe;

import org.junit.jupiter.api.*;

import java.util.concurrent.TimeUnit;

import com.jasperlorelai.circe.parser.Parser;
import com.jasperlorelai.circe.exeption.ParserException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@TestMethodOrder(MethodOrderer.MethodName.class)
@Timeout(value = 1000 / 20, unit = TimeUnit.MILLISECONDS)
public class CirceTests {

	@Test
	void test00() {
		String code = """
            // single comment
            /*
             * multi comment
             */
            """;
		assertEquals("", new Parser().parse(code));
	}

	@Test
	void test01() {
		String code = "";
		assertEquals("", new Parser().parse(code));
	}

	@Test
	void test02() {
		String code = "3";
		assertEquals("3", new Parser().parse(code));
	}

	@Test
	void test03() {
		String code = "    5   ";
		assertEquals("5", new Parser().parse(code));
	}

	@Test
	void test04() {
		String code = "-3.5";
		assertEquals("-3.5", new Parser().parse(code));
	}

	@Test
	void test05() {
		String code = ".5";
		assertEquals(".5", new Parser().parse(code));
	}

	@Test
	void test06() {
		String code = "  \" test \\\"123\\\"  \"  ";
		assertEquals(" test \"123\"  ", new Parser().parse(code));
	}

	@Test
	void test07() {
		String code = "at";
		String message = "Expected expression, instead received: VARIABLE_NAME 'at'";
		assertThrows(ParserException.class, () -> new Parser().parse(code), message);
	}

	@Test
	void test08() {
		String code = "at(3)";
		String message = "Expected expression, instead received: VARIABLE_NAME 'at'";
		assertThrows(ParserException.class, () -> new Parser().parse(code), message);
	}

	@Test
	void test09() {
		String code = "[2";
		String message = "Array does not end with ']'";
		assertThrows(ParserException.class, () -> new Parser().parse(code), message);
	}

	@Test
	void test10() {
		String code = "[]";
		assertEquals("[]", new Parser().parse(code));
	}

	@Test
	void test11() {
		String code = "[1]";
		assertEquals("[1]", new Parser().parse(code));
	}

	@Test
	void test12() {
		String code = ",[1]";
		String message = "Expected expression, instead received: COMMA ','";
		assertThrows(ParserException.class, () -> new Parser().parse(code), message);
	}

	@Test
	void test13() {
		String code = "[\"test\", -6]";
		assertEquals("[\"test\", -6]", new Parser().parse(code));
	}

	@Test
	void test14() {
		String code = "size(3)";
		String message = "Function 'size' has argument of NUMBER type defined at position 1 when ARRAY or STRING was expected.";
		assertThrows(ParserException.class, () -> new Parser().parse(code), message);
	}

	@Test
	void test15() {
		String code = "charAt(charAt(charAt(charAt(\"test\", 1), 0), 0), 0)";
		assertEquals("e", new Parser().parse(code));
	}

	@Test
	void test16() {
		String code = "endsWith(\"testFun\", \"Fun\")";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test17() {
		String code = "lowercase(\"ABCdef\")";
		assertEquals("abcdef", new Parser().parse(code));
	}

	@Test
	void test18() {
		String code = "padEnd(\"a\", 2, \"0\")";
		assertEquals("a0", new Parser().parse(code));
	}

	@Test
	void test19() {
		String code = "padStart(\"a\", 2, \"0\")";
		assertEquals("0a", new Parser().parse(code));
	}

	@Test
	void test20() {
		String code = "match(\"1a23b4\", \"\\d+\")";
		assertEquals("[\"1\", \"23\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test21() {
		String code = "match(\"1a23b4\", \"\\d+\")";
		assertEquals("[\"1\", \"23\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test22() {
		String code = "match(\"1a23b4\", \"[a-z]+\")";
		assertEquals("[\"a\", \"b\"]", new Parser().parse(code));
	}

	@Test
	void test23() {
		String code = "repeat(\"a\", 5)";
		assertEquals("aaaaa", new Parser().parse(code));
	}

	@Test
	void test24() {
		String code = "replaceAll(\"test123\", \"\\d+\", \"\")";
		assertEquals("test", new Parser().parse(code));
	}

	@Test
	void test25() {
		String code = "replaceAll(\"test123\", \"\\d+\", \"\")";
		assertEquals("test", new Parser().parse(code));
	}

	@Test
	void test26() {
		String code = "split(\"1a23b4\", \"[a-z]+\", 0)";
		assertEquals("[\"1\", \"23\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test27() {
		String code = "split(\"1a23b4\", \"[a-z]+\", 0)";
		assertEquals("[\"1\", \"23\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test28() {
		String code = "split(\"1a23b4\", \"[a-z]\", 0)";
		assertEquals("[\"1\", \"23\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test29() {
		String code = "split(\"1a23b4\", \"\", 0)";
		assertEquals("[\"1\", \"a\", \"2\", \"3\", \"b\", \"4\"]", new Parser().parse(code));
	}

	@Test
	void test30() {
		String code = "startsWith(\"test123\", \"test\")";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test31() {
		String code = "substring(\"test1 test2 test3\", indexOf(\"test1 test2 test3\", \" \"), lastIndexOf(\"test1 test2 test3\", \" \"))";
		assertEquals(" test2", new Parser().parse(code));
	}

	@Test
	void test32() {
		String code = "trim(\" 12  4   \")";
		assertEquals("12  4", new Parser().parse(code));
	}

	@Test
	void test33() {
		String code = "unicode(charCodeAt(\"a\", 0))";
		assertEquals("a", new Parser().parse(code));
	}

	@Test
	void test34() {
		String code = "uppercase(\"ABCdef\")";
		assertEquals("ABCDEF", new Parser().parse(code));
	}

	@Test
	void test35() {
		String code = "size(\"test\")";
		assertEquals("4", new Parser().parse(code));
	}

	@Test
	void test36() {
		String code = "contains([\"test1\", \"test2\", \"test3\"], \"test2\")";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test37() {
		String code = "contains(\"test1 test2 test3\", \"test2\")";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test38() {
		String code = "contains([1, 23, 4], 23)";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test39() {
		String code = "contains([1, \"23\", 4], 23)";
		assertEquals("false", new Parser().parse(code));
	}

	@Test
	void test40() {
		String code = "equals(\"test\", \"test\")";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test41() {
		String code = "equals([\"test\"], [\"test\"])";
		assertEquals("true", new Parser().parse(code));
	}

	@Test
	void test42() {
		String code = "equals(\"test\", [\"test\"])";
		assertEquals("false", new Parser().parse(code));
	}

	@Test
	void test43() {
		String code = "equals([\"test\"], \"test\")";
		assertEquals("false", new Parser().parse(code));
	}

	@Test
	void test44() {
		String code = "indexOf([\"1\", \"2\", \"3\"], \"3\")";
		assertEquals("2", new Parser().parse(code));
	}

	@Test
	void test45() {
		String code = "indexOf(\"123\", \"3\")";
		assertEquals("2", new Parser().parse(code));
	}

	@Test
	void test46() {
		String code = "lastIndexOf([1,2,3,4,1,2,3,1,2], 1)";
		assertEquals("7", new Parser().parse(code));
	}

	@Test
	void test47() {
		String code = "[trim(\"  test     \")]";
		assertEquals("[\"test\"]", new Parser().parse(code));
	}

	@Test
	void test48() {
		String code = "reverseString(\"abcdefg\")";
		assertEquals("gfedcba", new Parser().parse(code));
	}

	@Test
	void test49() {
		String code = "toString(2, 10)";
		assertEquals("2", new Parser().parse(code));
	}

	@Test
	void test50() {
		String code = "toString(10, 16)";
		assertEquals("a", new Parser().parse(code));
	}

	@Test
	void test51() {
		String code = "round(toNumber(\"1\"))";
		assertEquals("1", new Parser().parse(code));
	}

	@Test
	void test52() {
		String code = "toArray(\"\")";
		assertEquals("[\"\"]", new Parser().parse(code));
	}

	@Test
	void test53() {
		String code = "elementAt([\"test\"], 0)";
		assertEquals("test", new Parser().parse(code));
	}

	@Test
	void test54() {
		String code = "join([\"a\", \"b\", \"c\", \"d\"], \"\")";
		assertEquals("abcd", new Parser().parse(code));
	}

	@Test
	void test55() {
		String code = "reverseArray([\"a\", \"b\", \"c\", \"d\"])";
		assertEquals("[\"d\", \"c\", \"b\", \"a\"]", new Parser().parse(code));
	}

	@Test
	void test56() {
		String code = "pop([\"a\", \"b\", \"c\"])";
		assertEquals("c", new Parser().parse(code));
	}

	@Test
	void test57() {
		String code = "push([\"a\", \"b\"], \"c\")";
		assertEquals("[\"a\", \"b\", \"c\"]", new Parser().parse(code));
	}

	@Test
	void test58() {
		String code = "shift([\"a\", \"b\", \"c\"])";
		assertEquals("a", new Parser().parse(code));
	}

	@Test
	void test59() {
		String code = "slice([\"a\", \"b\", \"c\", \"d\", \"e\", \"f\"], 1, 4)";
		assertEquals("[\"b\", \"c\", \"d\", \"e\"]", new Parser().parse(code));
	}

	@Test
	void test60() {
		String code = "splice([\"a\", \"b\", \"c\", \"d\", \"e\", \"f\"], 1, 4, [\"z\"])";
		assertEquals("[\"a\", \"z\", \"f\"]", new Parser().parse(code));
	}

	@Test
	void test61() {
		String code = "subtract([\"a\", \"b\", \"c\"], [\"c\"])";
		assertEquals("[\"a\", \"b\"]", new Parser().parse(code));
	}

	@Test
	void test62() {
		String code = "unshift([\"b\", \"c\"], \"a\")";
		assertEquals("[\"a\", \"b\", \"c\"]", new Parser().parse(code));
	}

	@Test
	void test63() {
		String code = "$string = \"test1 test2 test3\"; $i = indexOf($string, \" \"); $i";
		assertEquals("5", new Parser().parse(code));
	}

	@Test
	void test64() {
		String code = "$string = \"test1 test2 test3\"; substring($string, calc($i = indexOf($string, \" \"); \"i + 1\"), lastIndexOf($string, \" \"))";
		assertEquals("test2", new Parser().parse(code));
	}

	@Test
	void test65() {
		String code = """
                // test
                $string = "test1 test2 test3";
                $index = indexOf(
                    $string ,
                    " "
                );
                /**/
                /*t
                 * est */
                /*
                 * test
                 */
                $start = calc(
                    "index + 1"
                );
                $end = lastIndexOf(
                    $string,
                    " "
                );
                substring(
                    $string,
                    $start,
                    $end
                );
                """;
		assertEquals("test2", new Parser().parse(code));
	}

	@Test
	void test66() {
		String code = """
            $color1 = 255; $color2 = 255; $color3 = 255;
            join(["#",
            padStart(toString($color1, 16), 2, "0"),
            padStart(toString($color2, 16), 2, "0"),
            padStart(toString($color3, 16), 2, "0")
            ], "")
            """;
		assertEquals("#ffffff", new Parser().parse(code));
	}

}

