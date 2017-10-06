package blanco.apex.formatter;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import blanco.apex.parser.BlancoApexParserUtil;
import blanco.apex.parser.token.BlancoApexToken;

public class BlancoApexFormatter003Test {
	private static final String TARGET_FILE1 = "./test/data/apex/MySimpleTest2.cls";

	@Test
	public void test() throws IOException {
		final List<BlancoApexToken> tokenList = new BlancoApexFormatter(new BlancoApexFormatterSettings())
				.format(new File(TARGET_FILE1)); //
		if (false)
			System.out.println(BlancoApexParserUtil.tokenList2String(tokenList));
		if (true)
			System.out.println(tokenList.get(0).getDisplayString());

		assertEquals(
				"SOURCE[ANNOTATION[SPECIAL_CHAR[@],WORD[isTest],NEWLINE[n]],CLASS[MODIFIER[WORD[public],WHITESPACE[ ],WORD[without],WHITESPACE[ ],WORD[sharing]],WHITESPACE[ ],WORD[class],WHITESPACE[ ],WORD[MySimpleTest],WHITESPACE[ ],BLOCK(CLASS_DEF)[SPECIAL_CHAR[{],NEWLINE[n],WHITESPACE[    ],METHOD[MODIFIER[WORD[static],WHITESPACE[ ],WORD[testMethod]]WHITESPACE[ ]TYPE[WORD[void]]WHITESPACE[ ]WORD[testMain002]PARENTHESIS[SPECIAL_CHAR[(],SPECIAL_CHAR[)]]WHITESPACE[ ]BLOCK(METHOD_DEF)[SPECIAL_CHAR[{],NEWLINE[n],WHITESPACE[        ],STATEMENT[WORD[System],SPECIAL_CHAR[.],WORD[assert],PARENTHESIS[SPECIAL_CHAR[(],WORD[false],SPECIAL_CHAR[,],WHITESPACE[ ],LITERAL(STRING)['Hello test world!'],SPECIAL_CHAR[)]],SPECIAL_CHAR[;]],NEWLINE[n],WHITESPACE[    ],SPECIAL_CHAR[}]]],NEWLINE[n],NEWLINE[n],WHITESPACE[    ],METHOD[MODIFIER[WORD[static]]WHITESPACE[ ]TYPE[WORD[void]]WHITESPACE[ ]WORD[test001]PARENTHESIS[SPECIAL_CHAR[(],SPECIAL_CHAR[)]]WHITESPACE[ ]BLOCK(METHOD_DEF)[SPECIAL_CHAR[{],NEWLINE[n],WHITESPACE[        ],STATEMENT[WORD[Integer],WHITESPACE[ ],WORD[i],WHITESPACE[ ],SPECIAL_CHAR[=],WHITESPACE[ ],LITERAL(NUMBER)[0],SPECIAL_CHAR[;]],NEWLINE[n],WHITESPACE[        ],WHILE_STATEMENT[WORD[while],WHITESPACE[ ],PARENTHESIS[SPECIAL_CHAR[(],LITERAL(NUMBER)[5],SPECIAL_CHAR[>],WHITESPACE[ ],WORD[i],SPECIAL_CHAR[)]],WHITESPACE[ ],BLOCK(MULTI_STATEMENT)[SPECIAL_CHAR[{],NEWLINE[n],WHITESPACE[            ],STATEMENT[WORD[i],SPECIAL_CHAR[++],SPECIAL_CHAR[;]],NEWLINE[n],WHITESPACE[        ],SPECIAL_CHAR[}]]],NEWLINE[n],WHITESPACE[        ],STATEMENT[WORD[return],SPECIAL_CHAR[;]],NEWLINE[n],WHITESPACE[    ],SPECIAL_CHAR[}]]],NEWLINE[n],WHITESPACE[],SPECIAL_CHAR[}]]],NEWLINE[n]]",
				tokenList.get(0).getDisplayString());
	}
}
