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
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;

/**
 * Lexical whitespace formatter. tab into 4 spaces and FULL_WIDTH_SPACE into 2
 * spaces.
 * 
 * <ul>
 * <li>tab into 4 spaces.</li>
 * <li>FULL_WIDTH_SPACE into 2 spaces.</li>
 * </ul>
 * 
 * @author Toshiki Iga
 */
public class BlancoApexLexicalWhitespaceFormatter {
    /**
     * Format given list of token.
     * 
     * @param tokenList
     *            List of token to format.
     */
    public void format(final List<BlancoApexToken> tokenList) {
        // process simple normalize.
        for (BlancoApexToken lookup : tokenList) {
            // normalize tab and FULL_WIDTH_SPACE
            normalizeToken(lookup);
        }

        //////////////////////////////////////
        // process relative normalize.

        // remove whitespace only line.
        for (int index = 1; index < tokenList.size() - 1; index++) {
            if (tokenList.get(index) instanceof BlancoApexWhitespaceToken) {
                if (tokenList.get(index - 1) instanceof BlancoApexNewlineToken
                        && tokenList.get(index + 1) instanceof BlancoApexNewlineToken) {
                    // remove whitespace only line.
                    tokenList.remove(index);
                    index--;
                }
            }
        }

        // remove whitespace that end line with whitespace.
        for (int index = 0; index < tokenList.size() - 1; index++) {
            if (tokenList.get(index) instanceof BlancoApexWhitespaceToken
                    && tokenList.get(index + 1) instanceof BlancoApexNewlineToken) {
                // remove whitespace that end line with whitespace.
                tokenList.remove(index);
            }
        }

        // remove trailing newline
        for (int index = 0; index < tokenList.size() - 2; index++) {
            if (tokenList.get(index) instanceof BlancoApexNewlineToken
                    && tokenList.get(index + 1) instanceof BlancoApexNewlineToken
                    && tokenList.get(index + 2) instanceof BlancoApexNewlineToken) {
                // remove whitespace only line.
                tokenList.remove(index + 2);
            }
        }
    }

    /**
     * normalize tab and FULL_WIDTH_SPACE
     * 
     * <ul>
     * <li>tab into 4 spaces.</li>
     * <li>FULL_WIDTH_SPACE into 2 spaces.</li>
     * </ul>
     * 
     * @param lookup
     *            Target token to normalize.
     */
    public static void normalizeToken(final BlancoApexToken lookup) {
        if (lookup instanceof BlancoApexWhitespaceToken) {
            final BlancoApexWhitespaceToken token = (BlancoApexWhitespaceToken) lookup;

            // tab into 4 spaces.
            if (token.getValue().indexOf('\t') >= 0) {
                token.setValue(token.getValue().replaceAll("\t", "    "));
            }

            // FULL_WIDTH_SPACE into 2 spaces.
            if (token.getValue().indexOf('　') >= 0/* FULL_WIDTH_SPACE */) {
                token.setValue(token.getValue().replaceAll("　", "  "));
            }
        }
    }
}
