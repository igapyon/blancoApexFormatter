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
import blanco.apex.parser.token.BlancoApexSpecialCharToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.parser.token.BlancoApexWordToken;

public class BlancoApexIndentNormalizer {
	/**
	 * main normalize method.
	 * 
	 * @param tokenList
	 */
	public void normalize(final List<BlancoApexToken> tokenList) {
		int indentLevel = 0;
		boolean isJustAfterLineTerminate = true;
		for (int index = 1; index < tokenList.size() - 1; index++) {
			final BlancoApexToken centerToken = tokenList.get(index);
			final BlancoApexToken leftToken = tokenList.get(index - 1);
			final BlancoApexToken rightToken = tokenList.get(index + 1);

			// need syntac parse to do format.

			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase("{")) {
				indentLevel++;
				isJustAfterLineTerminate = true;
			}
			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase("(")) {
				indentLevel++;
			}
			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase("[")) {
				indentLevel++;
			}
			if (rightToken instanceof BlancoApexSpecialCharToken && rightToken.getValue().equalsIgnoreCase("}")) {
				indentLevel--;
			}
			if (rightToken instanceof BlancoApexSpecialCharToken && rightToken.getValue().equalsIgnoreCase(")")) {
				indentLevel--;
			}
			if (rightToken instanceof BlancoApexSpecialCharToken && rightToken.getValue().equalsIgnoreCase("]")) {
				indentLevel--;
			}

			if (centerToken instanceof BlancoApexWhitespaceToken && leftToken instanceof BlancoApexNewlineToken) {
				final StringBuffer space = new StringBuffer();
				for (int loop = 0; loop < indentLevel; loop++) {
					space.append("    ");
				}

				if (isJustAfterLineTerminate == false) {
					space.append("    ");
				}

				centerToken.setValue(space.toString());
			}

			if (centerToken instanceof BlancoApexWordToken
			/* || centerToken instanceof BlancoApexSystemClassToken */) {
				isJustAfterLineTerminate = false;
			}

			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase("}")) {
				isJustAfterLineTerminate = true;
			}
			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase(")")) {
				isJustAfterLineTerminate = false;
			}

			if (centerToken instanceof BlancoApexSpecialCharToken && centerToken.getValue().equalsIgnoreCase(";")) {
				isJustAfterLineTerminate = true;
			}
		}
	}
}
