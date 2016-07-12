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

import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWordToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxConstants;

/**
 * Normalize word case.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexWordCaseNormalizer {
	public void normalize(final List<BlancoApexToken> tokenList) {
		for (BlancoApexToken lookup : tokenList) {
			normalizeToken(lookup);
		}
	}

	public static void normalizeToken(final BlancoApexToken lookup) {
		if (lookup instanceof BlancoApexWordToken) {
			final BlancoApexWordToken token = (BlancoApexWordToken) lookup;
			for (String keyword : BlancoApexSyntaxConstants.APEX_SYSTEM_CLASSES) {
				if (token.getValue().compareToIgnoreCase(keyword) == 0) {
					if (token.getValue().equals(keyword) == false) {
						// change case
						// System.out.println("normalize: from[" +
						// token.getValue() + "], to[" + keyword + "]");
						token.setValue(keyword);
					}
					return;
				}
			}

			System.out.println("Unexpected state: BlancoApexSystemClassToken: " + token.getDisplayString());
		}

		if (lookup instanceof BlancoApexWordToken) {
			final BlancoApexWordToken token = (BlancoApexWordToken) lookup;
			for (String keyword : BlancoApexSyntaxConstants.RESERVED_KEYWORDS) {
				if (token.getValue().compareToIgnoreCase(keyword) == 0) {
					if (token.getValue().equals(keyword) == false) {
						// change case
						// System.out.println("normalize: from[" +
						// token.getValue() + "], to[" + keyword + "]");
						token.setValue(keyword);
					}
					return;
				}
			}
			for (String keyword : BlancoApexSyntaxConstants.MAYBE_KEYWORDS) {
				if (token.getValue().compareToIgnoreCase(keyword) == 0) {
					if (token.getValue().equals(keyword) == false) {
						// change case
						// System.out.println("normalize: from[" +
						// token.getValue() + "], to[" + keyword + "]");
						token.setValue(keyword);
					}
					return;
				}
			}

			System.out.println("Unexpected state: BlancoApexReservedKeywordToken: " + token.getDisplayString());
		}
	}
}
