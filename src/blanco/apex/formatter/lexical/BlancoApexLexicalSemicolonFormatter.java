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
package blanco.apex.formatter.lexical;

import java.util.List;

import blanco.apex.parser.token.BlancoApexNewlineToken;
import blanco.apex.parser.token.BlancoApexSpecialCharToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;

/**
 * Format semicolon.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexLexicalSemicolonFormatter {
	/**
	 * main format method.
	 * 
	 * @param tokenList
	 */
	public void format(final List<BlancoApexToken> tokenList) {
		// ;a to ; a
		for (int index = 0; index < tokenList.size() - 1; index++) {
			final BlancoApexToken centerToken = tokenList.get(index);
			final BlancoApexToken rightToken = tokenList.get(index + 1);
			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase(";")) {
				if (false == rightToken instanceof BlancoApexWhitespaceToken
						&& false == rightToken instanceof BlancoApexNewlineToken) {
					// adding whitespace
					final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(" ", -1);
					tokenList.add(index + 1, newToken);
				}
			}
		}
	}
}
