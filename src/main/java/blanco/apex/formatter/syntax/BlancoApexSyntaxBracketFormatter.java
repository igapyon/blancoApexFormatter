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
package blanco.apex.formatter.syntax;

import java.util.List;

import blanco.apex.parser.token.BlancoApexNewlineToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.parser.token.BlancoApexWordToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxUtil;
import blanco.apex.syntaxparser.token.AbstractBlancoApexSyntaxToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBlockToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxParenthesisToken;

/**
 * Format indent.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexSyntaxBracketFormatter {
	/**
	 * main format method.
	 * 
	 * @param tokenList
	 */
	public void format(final List<BlancoApexToken> tokenList) {
		// process relative normalize.

		internalFormat(tokenList, new BlancoApexSyntaxBlockToken());
	}

	protected void internalFormat(final List<BlancoApexToken> tokenList, final AbstractBlancoApexSyntaxToken parent) {
		// remove
		// K&R
		for (int index = 0; index < tokenList.size(); index++) {
			if (index < tokenList.size() - 2) {
				if (tokenList.get(index + 1) instanceof BlancoApexNewlineToken
						&& tokenList.get(index + 2) instanceof BlancoApexSyntaxBlockToken) {
					tokenList.remove(index + 1);
				}
			}
			if (index < tokenList.size() - 3) {
				if (tokenList.get(index + 1) instanceof BlancoApexNewlineToken
						&& tokenList.get(index + 2) instanceof BlancoApexWhitespaceToken
						&& tokenList.get(index + 3) instanceof BlancoApexSyntaxBlockToken) {
					tokenList.get(index + 2).setValue(" ");
					tokenList.remove(index + 1);
				}
			}
		}

		for (int index = 0; index < tokenList.size(); index++) {
			if (tokenList.get(index) instanceof AbstractBlancoApexSyntaxToken) {
				internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList(),
						((AbstractBlancoApexSyntaxToken) tokenList.get(index)));
			}

			if (tokenList.get(index) instanceof BlancoApexWordToken) {
				final BlancoApexWordToken wordChar = (BlancoApexWordToken) tokenList.get(index);
				if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(wordChar.getValue(),
						new String[] { "if", "for", "while" })) {
					if (index < tokenList.size() - 1) {
						final BlancoApexToken rightToken = tokenList.get(index + 1);
						if (rightToken instanceof BlancoApexSyntaxParenthesisToken) {
							tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
						}
					}
				}
				if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(wordChar.getValue(), new String[] { "try" })) {
					if (index < tokenList.size() - 1) {
						final BlancoApexToken rightToken = tokenList.get(index + 1);
						if (rightToken instanceof BlancoApexSyntaxBlockToken) {
							tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
						}
					}
				}
			} else if (tokenList.get(index) instanceof BlancoApexSyntaxParenthesisToken) {
				if (index < tokenList.size() - 1) {
					final BlancoApexToken rightToken = tokenList.get(index + 1);
					if (rightToken instanceof BlancoApexSyntaxBlockToken) {
						tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
					}
				}
			} else if (tokenList.get(index) instanceof BlancoApexSyntaxBlockToken) {
				if (index < tokenList.size() - 1) {
					final BlancoApexToken rightToken = tokenList.get(index + 1);
					if (rightToken instanceof BlancoApexWordToken) {
						final BlancoApexWordToken wordChar = (BlancoApexWordToken) tokenList.get(index + 1);
						if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(wordChar.getValue(),
								new String[] { "catch", "finally", "else" })) {
							tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
						}
					}
				}
			}
		}
	}
}
