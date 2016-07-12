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

import java.util.List;

import blanco.apex.parser.token.BlancoApexNewlineToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.parser.token.BlancoApexWordToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxUtil;
import blanco.apex.syntaxparser.token.AbstractBlancoApexSyntaxToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBlockToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxIfStatementToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxParenthesisToken;

/**
 * Normalize bracket.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexBracketNormalizer {
	public void cleanupTailWhitespace(final List<BlancoApexToken> tokenList) {
		// process relative normalize.

		// call children if syntax token.
		for (int index = 0; index < tokenList.size(); index++) {
			if (tokenList.get(index) instanceof AbstractBlancoApexSyntaxToken) {
				cleanupTailWhitespace(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList());
				continue;
			}
		}

		// cleanup ) { to ){
		for (int index = 0; index < tokenList.size() - 2; index++) {
			if (tokenList.get(index) instanceof BlancoApexSyntaxParenthesisToken
					&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
					&& tokenList.get(index + 2) instanceof BlancoApexSyntaxBlockToken) {
				tokenList.remove(index + 1);
				index--;
			}
		}

		// cleanup )\n { to ){
		for (int index = 0; index < tokenList.size() - 3; index++) {
			if (tokenList.get(index) instanceof BlancoApexSyntaxParenthesisToken
					&& tokenList.get(index + 1) instanceof BlancoApexNewlineToken
					&& tokenList.get(index + 2) instanceof BlancoApexWhitespaceToken
					&& tokenList.get(index + 3) instanceof BlancoApexSyntaxBlockToken) {
				tokenList.remove(index + 2);
				tokenList.remove(index + 1);
				index--;
			}
		}

		// cleanup if ( to if(
		for (int index = 0; index < tokenList.size() - 2; index++) {
			if ((tokenList.get(index) instanceof BlancoApexWordToken && BlancoApexSyntaxUtil
					.isIncludedIgnoreCase(tokenList.get(index).getValue(), new String[] { "if", "for", "do", "catch" }))
					&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
					&& tokenList.get(index + 2) instanceof BlancoApexSyntaxParenthesisToken) {
				tokenList.remove(index + 1);
				index--;
			}
		}

		// cleanup } word to }word
		for (int index = 0; index < tokenList.size() - 2; index++) {
			if (tokenList.get(index) instanceof BlancoApexSyntaxBlockToken
					&& tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken
					&& tokenList.get(index + 2) instanceof BlancoApexWordToken) {
				tokenList.remove(index + 1);
				index--;
			}
		}
	}

	/**
	 * main normalize method.
	 * 
	 * @param tokenList
	 */
	public void normalize(final List<BlancoApexToken> tokenList) {
		// process relative normalize.

		// cleanup
		cleanupTailWhitespace(tokenList);

		// from if( to if (
		if (false)
			for (int index = 1; index < tokenList.size(); index++) {
				final BlancoApexToken centerToken = tokenList.get(index);

				if (centerToken instanceof AbstractBlancoApexSyntaxToken) {
					normalize(((AbstractBlancoApexSyntaxToken) centerToken).getTokenList());
					continue;
				}

				final BlancoApexToken leftToken = tokenList.get(index - 1);
				if (centerToken instanceof BlancoApexSyntaxParenthesisToken) {
					if (leftToken instanceof BlancoApexSyntaxIfStatementToken) {
						// adding whitespace
						final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(" ", -1);
						tokenList.add(index, newToken);
					} else if (leftToken instanceof BlancoApexSyntaxIfStatementToken) {
						// adding whitespace
						final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(" ", -1);
						tokenList.add(index, newToken);
					}
					// TODO do - while
				}
			}

		// from ){ to ) {
		if (false)
			for (int index = 1; index < tokenList.size() - 1; index++) {
				final BlancoApexToken centerToken = tokenList.get(index);

				if (centerToken instanceof AbstractBlancoApexSyntaxToken) {
					normalize(((AbstractBlancoApexSyntaxToken) centerToken).getTokenList());
					continue;
				}

				final BlancoApexToken rightToken = tokenList.get(index + 1);
				if (centerToken instanceof BlancoApexSyntaxParenthesisToken) {
					if (rightToken instanceof BlancoApexSyntaxBlockToken) {
						// adding whitespace
						final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(" ", -1);
						tokenList.add(index + 1, newToken);
					}
				}
			}
	}
}
