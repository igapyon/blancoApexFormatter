package blanco.apex.formatter;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import blanco.apex.parser.BlancoApexParserUtil;
import blanco.apex.parser.token.BlancoApexToken;

public class BlancoApexFormatter002Test {
	private static final String TARGET_FILE1 = "./test/data/apex/MySimpleTest.cls";
//	private static final String TARGET_FILE1 = "/home/tosiki/workspace/apex-lang/src.apex/ArrayUtils.cls";

	@Test
	public void test() throws IOException {
		final List<BlancoApexToken> tokenList = new BlancoApexFormatter().format(new File(TARGET_FILE1)); //
		System.out.println(BlancoApexParserUtil.tokenList2String(tokenList));
	}
}
