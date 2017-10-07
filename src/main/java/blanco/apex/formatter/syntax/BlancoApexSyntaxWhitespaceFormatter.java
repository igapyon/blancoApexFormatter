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
import blanco.apex.syntaxparser.token.AbstractBlancoApexSyntaxToken;

/**
 * Trailing whitespace formatter.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexSyntaxWhitespaceFormatter {
    /**
     * main format method.
     * 
     * @param tokenList
     */
    public void format(final List<BlancoApexToken> tokenList) {
        internalFormat(tokenList);
    }

    protected void internalFormat(final List<BlancoApexToken> tokenList) {
        for (int index = 0; index < tokenList.size(); index++) {
            if (tokenList.get(index) instanceof AbstractBlancoApexSyntaxToken) {
                internalFormat(((AbstractBlancoApexSyntaxToken) tokenList.get(index)).getTokenList());
            }

            if (index < tokenList.size() - 1) {
                // needs +1 size
                if (tokenList.get(index) instanceof BlancoApexWhitespaceToken) {
                    if (tokenList.get(index + 1) instanceof BlancoApexNewlineToken) {
                        tokenList.remove(index);
                    }
                }
            }
        }
    }
}
