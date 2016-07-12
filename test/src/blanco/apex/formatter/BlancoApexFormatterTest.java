package blanco.apex.formatter;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import blanco.apex.parser.BlancoApexParserUtil;
import blanco.apex.parser.token.BlancoApexToken;

public class BlancoApexFormatterTest {

	@Test
	public void test() throws IOException {
		final List<BlancoApexToken> tokenList = new BlancoApexFormatter().format("public class myOuterClass {\n" //
				+ "    // code here  \n" //
				+ "    class myInnerClass   {\n" //
				+ "\t// inner code here\n" //
				+ "   }  \n" //
				+ "}"); //
		System.out.println(BlancoApexParserUtil.tokenList2String(tokenList));
	}
}
