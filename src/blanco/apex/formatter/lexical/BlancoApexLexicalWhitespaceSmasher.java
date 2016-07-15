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

import blanco.apex.parser.token.BlancoApexLiteralToken;
import blanco.apex.parser.token.BlancoApexNewlineToken;
import blanco.apex.parser.token.BlancoApexSpecialCharToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.parser.token.BlancoApexWordToken;

/**
 * Smash whitespaces. for ease of test purpose.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexLexicalWhitespaceSmasher {
	/**
	 * entry point.
	 * 
	 * @param tokenList
	 */
	public void smash(final List<BlancoApexToken> tokenList) {

		for (boolean isProcessed = false;; isProcessed = false) {
			// smash head of line whitespace
			for (int index = 0; index < tokenList.size() - 1; index++) {
				if (tokenList.get(index) instanceof BlancoApexNewlineToken
						&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken) {
					tokenList.remove(index + 1);
					isProcessed = true;
				}
			}

			// smash WORD|LITERAL WHITESPACE SPECIAL
			for (int index = 0; index < tokenList.size() - 2; index++) {
				if ((tokenList.get(index) instanceof BlancoApexWordToken
						|| tokenList.get(index) instanceof BlancoApexLiteralToken)
						&& (tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
								|| tokenList.get(index + 1) instanceof BlancoApexNewlineToken)
						&& tokenList.get(index + 2) instanceof BlancoApexSpecialCharToken) {
					tokenList.remove(index + 1);
					isProcessed = true;
				}
			}

			// smash SPECIAL WHITESPACE SPECIAL
			for (int index = 0; index < tokenList.size() - 2; index++) {
				if (tokenList.get(index) instanceof BlancoApexSpecialCharToken
						&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
						&& tokenList.get(index + 2) instanceof BlancoApexSpecialCharToken) {
					tokenList.remove(index + 1);
					isProcessed = true;
				}
			}

			// smash SPECIAL WHITESPACE WORD|LITERAL
			for (int index = 0; index < tokenList.size() - 2; index++) {
				if (tokenList.get(index) instanceof BlancoApexSpecialCharToken
						&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
						&& (tokenList.get(index + 2) instanceof BlancoApexWordToken
								|| tokenList.get(index + 2) instanceof BlancoApexLiteralToken)) {
					tokenList.remove(index + 1);
					isProcessed = true;
				}
			}

			// smash trailing whitespace
			for (int index = 0; index < tokenList.size(); index++) {
				if (tokenList.get(index) instanceof BlancoApexWhitespaceToken) {
					final BlancoApexWhitespaceToken look = (BlancoApexWhitespaceToken) tokenList.get(index);
					if (look.getValue().length() > 1) {
						look.setValue(" ");
						isProcessed = true;
					}
				}
			}

			if (isProcessed == false) {
				break;
			}
		}
	}
}
