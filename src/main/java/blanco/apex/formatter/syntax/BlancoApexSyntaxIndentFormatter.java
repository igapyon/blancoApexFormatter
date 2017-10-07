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
import blanco.apex.parser.token.BlancoApexSpecialCharToken;
import blanco.apex.parser.token.BlancoApexToken;
import blanco.apex.parser.token.BlancoApexWhitespaceToken;
import blanco.apex.syntaxparser.BlancoApexSyntaxUtil;
import blanco.apex.syntaxparser.token.AbstractBlancoApexSyntaxToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBlockToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxBoxBracketsToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxClassToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxFieldToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxForStatementToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxIfStatementToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxMethodToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxParenthesisToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxPropertyToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxSOQLToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxStatementToken;
import blanco.apex.syntaxparser.token.BlancoApexSyntaxWhileStatementToken;

/**
 * Syntax indent formatter.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexSyntaxIndentFormatter {
    /**
     * main format method.
     * 
     * @param tokenList
     */
    public void format(final List<BlancoApexToken> tokenList) {
        // process relative normalize.

        internalFormat(tokenList, new BlancoApexSyntaxBlockToken(), 0);
    }

    protected void internalFormat(final List<BlancoApexToken> tokenList, final AbstractBlancoApexSyntaxToken parent,
            final int indentLevel) {
        for (int index = 0; index < tokenList.size(); index++) {
            if (tokenList.get(index) instanceof AbstractBlancoApexSyntaxToken) {
                if (tokenList.get(index) instanceof BlancoApexSyntaxBlockToken
                        || tokenList.get(index) instanceof BlancoApexSyntaxParenthesisToken) {
                    internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList(),
                            ((AbstractBlancoApexSyntaxToken) tokenList.get(index)), indentLevel + 1);
                } else if (tokenList.get(index) instanceof BlancoApexSyntaxSOQLToken) {
                    // do nothing about SOQL
                } else if (tokenList.get(index) instanceof BlancoApexSyntaxBoxBracketsToken) {
                    internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList(),
                            ((AbstractBlancoApexSyntaxToken) tokenList.get(index)), indentLevel + 1);
                } else {
                    internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList(),
                            ((AbstractBlancoApexSyntaxToken) tokenList.get(index)), indentLevel);
                }
                continue;
            }

            if (index < tokenList.size() - 1) {
                // needs +1 size
                if (tokenList.get(index) instanceof BlancoApexNewlineToken) {
                    if (tokenList.get(index + 1) instanceof BlancoApexSyntaxClassToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxMethodToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxFieldToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxPropertyToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxStatementToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxIfStatementToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxForStatementToken
                            || tokenList.get(index + 1) instanceof BlancoApexSyntaxWhileStatementToken) {
                        final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(
                                getIndentString(indentLevel), -1);
                        tokenList.add(index + 1, newToken);
                    } else if (tokenList.get(index + 1) instanceof BlancoApexSpecialCharToken) {
                        final BlancoApexSpecialCharToken special = (BlancoApexSpecialCharToken) tokenList
                                .get(index + 1);

                        if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(special.getValue(), new String[] { "}", ")" })) {
                            final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(
                                    getIndentString(indentLevel - 1), -1);
                            tokenList.add(index + 1, newToken);
                        } else {
                            final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(
                                    getIndentString(indentLevel), -1);
                            tokenList.add(index + 1, newToken);
                        }
                    } else if (false == tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken) {
                        final BlancoApexWhitespaceToken newToken = new BlancoApexWhitespaceToken(
                                getIndentString(indentLevel), -1);
                        tokenList.add(index + 1, newToken);
                    }
                }
            }

            // for existing whitespace token
            if (index < tokenList.size() - 2) {
                // needs +2 size
                if (tokenList.get(index) instanceof BlancoApexNewlineToken
                        && tokenList.get(index + 1) instanceof BlancoApexWhitespaceToken) {
                    final BlancoApexWhitespaceToken updateWhitespaceToken = (BlancoApexWhitespaceToken) tokenList
                            .get(index + 1);

                    if (tokenList.get(index + 2) instanceof BlancoApexSyntaxClassToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxMethodToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxFieldToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxPropertyToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxStatementToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxIfStatementToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxForStatementToken
                            || tokenList.get(index + 2) instanceof BlancoApexSyntaxWhileStatementToken) {
                        updateWhitespaceToken.setValue(getIndentString(indentLevel));
                    } else if (tokenList.get(index + 2) instanceof BlancoApexSpecialCharToken) {
                        final BlancoApexSpecialCharToken special = (BlancoApexSpecialCharToken) tokenList
                                .get(index + 2);

                        if (BlancoApexSyntaxUtil.isIncludedIgnoreCase(special.getValue(), new String[] { "}", ")" })) {
                            updateWhitespaceToken.setValue(getIndentString(indentLevel - 1));
                        } else {
                            updateWhitespaceToken.setValue(getIndentString(indentLevel));
                        }
                    } else {
                        updateWhitespaceToken.setValue(getIndentString(indentLevel));
                    }
                }
            }
        }
    }

    static final String getIndentString(final int level) {
        final StringBuffer strbuf = new StringBuffer();
        for (int index = 0; index < level * 4; index++) {
            strbuf.append(' ');
        }
        return strbuf.toString();
    }
}
