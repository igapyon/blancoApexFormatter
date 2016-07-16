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
import blanco.apex.formatter.lexical.BlancoApexLexicalSemicolonFormatter;
import blanco.apex.formatter.lexical.BlancoApexLexicalWhitespaceFormatter;
import blanco.apex.formatter.lexical.BlancoApexLexicalWhitespaceSmasher;
import blanco.apex.formatter.syntax.BlancoApexSyntaxBracketFormatter;
import blanco.apex.formatter.syntax.BlancoApexSyntaxIndentFormatter;
import blanco.apex.formatter.syntax.BlancoApexSyntaxSpecialCharFormatter;
import blanco.apex.formatter.syntax.BlancoApexSyntaxWhitespaceFormatter;
import blanco.apex.parser.BlancoApexParser;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxParser;

/**
 * Apex source file formatter.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexFormatter {
	protected BlancoApexFormatterSettings settings = null;

	public BlancoApexFormatter(final BlancoApexFormatterSettings settings) {
		this.settings = settings;
	}

	public final List<BlancoApexToken> format(final File file) throws IOException {
		final BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
		try {
			return format(reader);
		} finally {
			reader.close();
		}
	}

	public final List<BlancoApexToken> format(final String sourceString) throws IOException {
		final BufferedReader reader = new BufferedReader(new StringReader(sourceString));
		try {
			return format(reader);
		} finally {
			reader.close();
		}
	}

	public final List<BlancoApexToken> format(final BufferedReader reader) throws IOException {
		final List<BlancoApexToken> tokenList = new BlancoApexParser().parse(reader);
		return format(tokenList);
	}

	/**
	 * main normalize method.
	 * 
	 * @param tokenList
	 */
	public List<BlancoApexToken> format(final List<BlancoApexToken> tokenList) {
		/////////////////////////////////////
		// lexical format

		// process whitespace in lexical formatter.
		new BlancoApexLexicalWhitespaceFormatter().format(tokenList);

		if (settings.getSmashWhitespace()) {
			new BlancoApexLexicalWhitespaceSmasher().smash(tokenList);
		}

		if (settings.getFormatComma()) {
			new BlancoApexLexicalCommaFormatter().format(tokenList);
		}

		if (settings.getFormatSemicolon()) {
			new BlancoApexLexicalSemicolonFormatter().format(tokenList);
		}

		/////////////////////////////////////
		// syntax format

		// convert lexical list to syntax list.
		final List<BlancoApexToken> syntaxTokenList = new BlancoApexSyntaxParser().parse(tokenList);

		if (settings.getFormatIndent()) {
			new BlancoApexSyntaxIndentFormatter().format(syntaxTokenList);
		}

		if (settings.getFormatSpecialChar()) {
			new BlancoApexSyntaxSpecialCharFormatter().format(syntaxTokenList);
		}

		if (settings.getFormatBracket()) {
			new BlancoApexSyntaxBracketFormatter().format(syntaxTokenList);
		}

		// process whitespace in syntax formatter.
		new BlancoApexSyntaxWhitespaceFormatter().format(syntaxTokenList);

		return syntaxTokenList;
	}
}
