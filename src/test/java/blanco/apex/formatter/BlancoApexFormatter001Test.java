package blanco.apex.formatter;

import java.io.IOException;

import org.junit.Test;

public class BlancoApexFormatter001Test {

	@Test
	public void test() throws IOException {
		final String result = new BlancoApexFormatter(new BlancoApexFormatterSettings())
				.format("public class myOuterClass {\n" //
						+ "    // code here  \n" //
						+ "    class myInnerClass   {\n" //
						+ "\t// inner code here\n" //
						+ "   }  \n" //
						+ "}"); //
		System.out.println(result);
	}
}
