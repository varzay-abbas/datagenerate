package net.andreinc.mockneat.interfaces;

/*
 * Copyright 2017, Andrei N. Ciobanu

 Permission is hereby granted, free of charge, to any user obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation the
 rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit
 persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial portions of the
 Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, FREE_TEXT OF OR PARAM CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS PARAM THE SOFTWARE.
 */

import net.andreinc.mockneat.utils.ValidationUtils;
import net.andreinc.mockneat.types.enums.StringFormatType;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;

import java.io.UnsupportedEncodingException;
import java.util.function.Supplier;

import static java.net.URLEncoder.encode;
import static net.andreinc.aleph.AlephFormatter.template;
import static net.andreinc.mockneat.utils.ValidationUtils.notNull;
import static org.apache.commons.lang3.Validate.notEmpty;

@FunctionalInterface
public interface MockUnitString extends MockUnit<String> {

    default MockUnitString format(StringFormatType formatType) {
        notNull(formatType, "formatType");
        Supplier<String> supplier = () -> formatType.getFormatter().apply(supplier().get());
        return () -> supplier;
    }

    default MockUnitString sub(int endIndex) {
        return sub(0, endIndex);
    }

    default MockUnitString sub(int beginIndex, int endIndex) {
        Supplier<String> supplier = () -> supplier().get().substring(beginIndex, endIndex);
        return () -> supplier;
    }

    default MockUnitString append(String str) {
        notEmpty(str, "str");
        Supplier<String> supplier = () -> supplier().get().concat(str);
        return () -> supplier;
    }

    default MockUnitString prepend(String str) {
        notEmpty(str);
        Supplier<String> supplier = () -> str.concat(supplier().get());
        return () -> supplier;
    }

    default MockUnitString replace(char oldCHar, char newChar) {
        Supplier<String> supplier = () -> supplier().get().replace(oldCHar, newChar);
        return () -> supplier;
    }

    default MockUnitString replace(CharSequence target, CharSequence replacement) {
        Supplier<String> supplier = () -> supplier().get().replace(target, replacement);
        return () -> supplier;
    }

    default MockUnitString replaceAll(String regex, String replacement) {
        Supplier<String > supplier = () -> supplier().get().replaceAll(regex, replacement);
        return () -> supplier;
    }

    default MockUnitString replaceFirst(String regex, String replacement) {
        Supplier<String> supplier = () -> supplier().get().replaceFirst(regex, replacement);
        return () -> supplier;
    }

    default MockUnit<String[]> split(String regex, int limit) {
        Supplier<String[]> supplier = () -> supplier().get().split(regex, limit);
        return () -> supplier;
    }

    default MockUnit<String[]> split(String regex) {
        return split(regex, 0);
    }

    default MockUnitString urlEncode(String enc) {
        Supplier<String> supplier = () -> {
            String val = supplier().get();
            try {
                return encode(val, enc);
            } catch (UnsupportedEncodingException e) {
                String msg = template(ValidationUtils.CANNOT_URL_ENCODE_UTF_8, "val", val).fmt();
                throw new IllegalArgumentException(msg, e);
            }
        };
        return () -> supplier;
    }

    default MockUnitString urlEncode() {
        return urlEncode("UTF-8");
    }

    default MockUnitString noSpecialChars() {
        return () -> () -> supplier().get().replaceAll("[^\\dA-Za-z ]", "");
    }

    default MockUnitString escapeCsv() { return () -> () -> StringEscapeUtils.escapeCsv(supplier().get()); }

    default MockUnitString escapeEcmaScript() { return () -> () -> StringEscapeUtils.escapeEcmaScript(supplier().get()); }

    default MockUnitString escapeHtml() { return () -> () -> StringEscapeUtils.escapeHtml4(supplier().get()); }

    default MockUnitString escapeXml() { return() -> () -> StringEscapeUtils.escapeXml11(supplier().get()); }

    // TODO document methods

    default MockUnitString md2() { return () -> () -> DigestUtils.md2Hex(supplier().get()); }

    default MockUnitString md5() { return () -> () -> DigestUtils.md5Hex(supplier().get()); }

    default MockUnitString sha1() { return () -> () -> DigestUtils.sha1Hex(supplier().get()); }

    default MockUnitString sha256() { return () -> () -> DigestUtils.sha256Hex(supplier().get()); }

    default MockUnitString sha384() { return () -> () -> DigestUtils.sha384Hex(supplier().get()); }

    default MockUnitString sha512() { return () -> () -> DigestUtils.sha512Hex(supplier().get()); }

    default MockUnitString base64() { return () -> () -> new Base64().encodeAsString(supplier().get().getBytes()); }

    default MockUnit<String[]> array(int size) {
        return array(String.class, size);
    }
}