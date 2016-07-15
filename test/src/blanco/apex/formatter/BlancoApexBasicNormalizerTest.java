package blanco.apex.formatter;

import java.io.File;
import java.util.List;

import org.junit.Test;

import blanco.apex.parser.token.BlancoApexToken;

/**
 * Test method to try apex formatter.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexBasicNormalizerTest {
	private static final String TARGET_FILE1 = "./test/data/apex/MySimpleTest.cls";

	/**
	 * Input apex class file and output formatted apex class file onto System
	 * out.
	 * 
	 * Run this and see system standard output area.
	 * 
	 * @throws Exception
	 */
	@Test
	public void testFormatApexClass001() throws Exception {
		final List<BlancoApexToken> result = new BlancoApexBasicNormalizer().normalize(new File(TARGET_FILE1));

		// output parsed token list onto System.out.
		for (BlancoApexToken lookup : result) {
			System.out.print(lookup.getValue());
		}
	}
}
