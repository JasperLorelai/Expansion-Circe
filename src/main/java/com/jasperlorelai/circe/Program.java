package com.jasperlorelai.circe;

import com.jasperlorelai.circe.parser.Parser;

public class Program {

	public static void main(String[] args) {
		String code = String.join(" ", args);
		System.out.println(new Parser().parse(code));
	}

}
