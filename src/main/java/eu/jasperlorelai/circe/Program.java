package eu.jasperlorelai.circe;

import eu.jasperlorelai.circe.parser.Parser;

public class Program {

	public static void main(String[] args) {
		String code = String.join(" ", args);
		System.out.println(new Parser().parse(code));
	}

}
