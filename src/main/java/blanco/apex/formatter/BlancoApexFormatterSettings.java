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

/**
 * Settings info for blancoApexFormatter.
 * 
 * @author Toshiki Iga
 */
public class BlancoApexFormatterSettings {
    /////////////////////
    // lexical

    /**
     * Smash all whitespace, without comments. It does too much, so it is set as
     * false default.
     */
    protected boolean isSmashWhitespace = false;

    protected boolean isFormatComma = true;

    protected boolean isFormatSemicolon = true;

    /////////////////////
    // syntax

    protected boolean isFormatIndent = true;

    protected boolean isFormatSpecialChar = true;

    protected boolean isFormatBracket = true;

    public boolean getFormatComma() {
        return isFormatComma;
    }

    public void setFormatComma(boolean isFormatComma) {
        this.isFormatComma = isFormatComma;
    }

    public boolean getFormatSemicolon() {
        return isFormatSemicolon;
    }

    public void setFormatSemicolon(boolean isFormatSemicolon) {
        this.isFormatSemicolon = isFormatSemicolon;
    }

    public boolean getFormatIndent() {
        return isFormatIndent;
    }

    public void setFormatIndent(boolean isFormatIndent) {
        this.isFormatIndent = isFormatIndent;
    }

    public boolean getFormatSpecialChar() {
        return isFormatSpecialChar;
    }

    public void setFormatSpecialChar(boolean isFormatSpecialChar) {
        this.isFormatSpecialChar = isFormatSpecialChar;
    }

    public boolean getFormatBracket() {
        return isFormatBracket;
    }

    public void setFormatBracket(boolean isFormatBracket) {
        this.isFormatBracket = isFormatBracket;
    }

    public boolean getSmashWhitespace() {
        return isSmashWhitespace;
    }

    public void setSmashWhitespace(boolean isSmashWhitespace) {
        this.isSmashWhitespace = isSmashWhitespace;
    }
}
