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

import blanco.apex.parser.token.BlancoApexLiteralToken;
import blanco.apex.parser.token.BlancoApexSpecialCharToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.parser.token.BlancoApexWordToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxUtil;
import blanco.apex.syntaxparser.token.AbstractBlancoApexSyntaxToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBlockToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBoxBracketsToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxParenthesisToken;

/**
 * Format indent.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexSyntaxSpecialCharFormatter {
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
        for (int index = 0; index < tokenList.size(); index++) {
            if (tokenList.get(index) instanceof AbstractBlancoApexSyntaxToken) {
                internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList(),
                        ((AbstractBlancoApexSyntaxToken) tokenList.get(index)));
            }

            if (tokenList.get(index) instanceof BlancoApexSpecialCharToken) {
                final BlancoApexSpecialCharToken specialChar = (BlancoApexSpecialCharToken) tokenList.get(index);
                if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(specialChar.getValue(),
                        new String[] { "=", "==", "<=", ">=", "!=", "||", "&&", "+", "-", "*", "/",
                                "?"/* , ":" care about label */ , "+=", "-=", "=>" })) {
                    if (index < tokenList.size() - 1) {
                        final BlancoApexToken rightToken = tokenList.get(index + 1);
                        if (rightToken instanceof BlancoApexWordToken //
                                || rightToken instanceof BlancoApexLiteralToken //
                                || rightToken instanceof BlancoApexSyntaxParenthesisToken) {
                            tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
                        }
                    }
                    if (index > 0) {
                        final BlancoApexToken leftToken = tokenList.get(index - 1);
                        if (leftToken instanceof BlancoApexWordToken //
                                || leftToken instanceof BlancoApexLiteralToken //
                                || leftToken instanceof BlancoApexSyntaxParenthesisToken
                                || leftToken instanceof BlancoApexSyntaxBoxBracketsToken) {
                            tokenList.add(index, new BlancoApexWhitespaceToken(" ", -1));
                        }
                    }
                }

                // workaround for non supportig generics.
                if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(specialChar.getValue(), new String[] { ">" })) {
                    if (index < tokenList.size() - 1) {
                        final BlancoApexToken rightToken = tokenList.get(index + 1);
                        if (rightToken instanceof BlancoApexWordToken //
                                || rightToken instanceof BlancoApexLiteralToken //
                        /*
                         * || rightToken instanceof
                         * BlancoApexSyntaxParenthesisToken workaround for non
                         * generics support.
                         */) {
                            tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
                        }
                    }
                }
                // workaround for non supportig generics.
                if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(specialChar.getValue(), new String[] { "<" })) {
                    if (index > 0) {
                        final BlancoApexToken leftToken = tokenList.get(index - 1);
                        if (/*
                             * leftToken instanceof BlancoApexWordToken // ||
                             * workaround for non support generics.
                             */
                        leftToken instanceof BlancoApexLiteralToken //
                                || leftToken instanceof BlancoApexSyntaxParenthesisToken) {
                            tokenList.add(index, new BlancoApexWhitespaceToken(" ", -1));
                        }
                    }
                }

                // workaround for method def []
                if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(specialChar.getValue(), new String[] { "]" })) {
                    if (index < tokenList.size() - 1) {
                        final BlancoApexToken rightToken = tokenList.get(index + 1);
                        if (rightToken instanceof BlancoApexWordToken) {
                            tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
                        }
                    }
                }
            } else if (tokenList.get(index) instanceof BlancoApexSyntaxBoxBracketsToken) {
                if (index < tokenList.size() - 1) {
                    final BlancoApexToken rightToken = tokenList.get(index + 1);
                    if (rightToken instanceof BlancoApexWordToken) {
                        tokenList.add(index + 1, new BlancoApexWhitespaceToken(" ", -1));
                    }
                }
            }
        }
    }
}
