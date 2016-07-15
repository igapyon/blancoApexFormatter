/*
 * Copyright 2016 Toshiki Iga
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package blanco.apex.formatter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import blanco.apex.formatter.lexical.BlancoApexLexicalCommaFormatter;
import blanco.apex.formatter.lexical.BlancoApexLexicalWhitespaceFormatter;
import blanco.apex.parser.BlancoApexParser;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxParser;

/**
 * Normalize bracket.
 * 
 * under construction.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexBasicNormalizer {
	public final List<BlancoApexToken> normalize(final File file) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		try {
			return normalize(reader);
		} finally {
			reader.close();
		}
	}

	public final List<BlancoApexToken> normalize(final String sourceString) throws IOException {
		final BufferedReader reader = new BufferedReader(new StringReader(sourceString));
		try {
			return normalize(reader);
		} finally {
			reader.close();
		}
	}

	public final List<BlancoApexToken> normalize(final BufferedReader reader) throws IOException {
		final List<BlancoApexToken> tokenList = new BlancoApexParser().parse(reader);
		return normalize(tokenList);
	}

	/**
	 * main normalize method.
	 * 
	 * @param tokenList
	 */
	public List<BlancoApexToken> normalize(final List<BlancoApexToken> tokenList) {
		// process relative normalize.

		// need syntac parse to do format.

		// 1st whitespace normalize.
		new BlancoApexLexicalWhitespaceFormatter().format(tokenList);

		new BlancoApexLexicalCommaFormatter().format(tokenList);

		final List<BlancoApexToken> syntaxTokenList = new BlancoApexSyntaxParser().parse(tokenList);

		// It seems bad. currently disabled.
		// new BlancoApexWordCaseNormalizer().normalize(tokenList);

		new BlancoApexBracketNormalizer().normalize(syntaxTokenList);

//		new BlancoApexIndentNormalizer().normalize(syntaxTokenList);

		// 2nd whitespace normalize.
		// retry to normalize whitespace.
		new BlancoApexLexicalWhitespaceFormatter().format(syntaxTokenList);

		return syntaxTokenList;
	}
}
