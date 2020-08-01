package io.constructor.client;

import java.nio.charset.Charset;
import okhttp3.internal.Util;
import okio.Buffer;

import static okhttp3.internal.Util.decodeHexDigit;

/**
 * Utility file that contains code paths from https://square.github.io/okhttp/4.x/okhttp/okhttp3/-http-url/-builder/
 * that were otherwise unaccessible.  Line for line copy of the code needed to canonicalize URLs and define 
 * how plus signs are encoded.
 */
public class OkHttpUrlBuilderUtils {

  public static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
  public static final String PATH_SEGMENT_ENCODE_SET = " \"<>^`{}|/\\?#";

  public static boolean percentEncoded(String encoded, int pos, int limit) {
    return pos + 2 < limit
        && encoded.charAt(pos) == '%'
        && decodeHexDigit(encoded.charAt(pos + 1)) != -1
        && decodeHexDigit(encoded.charAt(pos + 2)) != -1;
  }

  /**
   * Returns a substring of {@code input} on the range {@code [pos..limit)} with the following
   * transformations:
   * <ul>
   *   <li>Tabs, newlines, form feeds and carriage returns are skipped.
   *   <li>In queries, ' ' is encoded to '+' and '+' is encoded to "%2B".
   *   <li>Characters in {@code encodeSet} are percent-encoded.
   *   <li>Control characters and non-ASCII characters are percent-encoded.
   *   <li>All other characters are copied without transformation.
   * </ul>
   *
   * @param alreadyEncoded true to leave '%' as-is; false to convert it to '%25'.
   * @param strict true to encode '%' if it is not the prefix of a valid percent encoding.
   * @param plusIsSpace true to encode '+' as "%2B" if it is not already encoded.
   * @param asciiOnly true to encode all non-ASCII codepoints.
   * @param charset which charset to use, null equals UTF-8.
   */
  public static String canonicalize(String input, int pos, int limit, String encodeSet,
      boolean alreadyEncoded, boolean strict, boolean plusIsSpace, boolean asciiOnly,
      Charset charset) {
    int codePoint;
    for (int i = pos; i < limit; i += Character.charCount(codePoint)) {
      codePoint = input.codePointAt(i);
      if (codePoint < 0x20
          || codePoint == 0x7f
          || codePoint >= 0x80 && asciiOnly
          || encodeSet.indexOf(codePoint) != -1
          || codePoint == '%' && (!alreadyEncoded || strict && !percentEncoded(input, i, limit))
          || codePoint == '+' && plusIsSpace) {
        // Slow path: the character at i requires encoding!
        Buffer out = new Buffer();
        out.writeUtf8(input, pos, i);
        canonicalize(out, input, i, limit, encodeSet, alreadyEncoded, strict, plusIsSpace,
            asciiOnly, charset);
        return out.readUtf8();
      }
    }

    // Fast path: no characters in [pos..limit) required encoding.
    return input.substring(pos, limit);
  }

  static void canonicalize(Buffer out, String input, int pos, int limit, String encodeSet,
      boolean alreadyEncoded, boolean strict, boolean plusIsSpace, boolean asciiOnly,
      Charset charset) {
    Buffer encodedCharBuffer = null; // Lazily allocated.
    int codePoint;
    for (int i = pos; i < limit; i += Character.charCount(codePoint)) {
      codePoint = input.codePointAt(i);
      if (alreadyEncoded
          && (codePoint == '\t' || codePoint == '\n' || codePoint == '\f' || codePoint == '\r')) {
        // Skip this character.
      } else if (codePoint == '+' && plusIsSpace) {
        // Encode '+' as '%2B' since we permit ' ' to be encoded as either '+' or '%20'.
        out.writeUtf8(alreadyEncoded ? "+" : "%2B");
      } else if (codePoint < 0x20
          || codePoint == 0x7f
          || codePoint >= 0x80 && asciiOnly
          || encodeSet.indexOf(codePoint) != -1
          || codePoint == '%' && (!alreadyEncoded || strict && !percentEncoded(input, i, limit))) {
        // Percent encode this character.
        if (encodedCharBuffer == null) {
          encodedCharBuffer = new Buffer();
        }

        if (charset == null || charset.equals(Util.UTF_8)) {
          encodedCharBuffer.writeUtf8CodePoint(codePoint);
        } else {
          encodedCharBuffer.writeString(input, i, i + Character.charCount(codePoint), charset);
        }

        while (!encodedCharBuffer.exhausted()) {
          int b = encodedCharBuffer.readByte() & 0xff;
          out.writeByte('%');
          out.writeByte(HEX_DIGITS[(b >> 4) & 0xf]);
          out.writeByte(HEX_DIGITS[b & 0xf]);
        }
      } else {
        // This character doesn't need encoding. Just copy it over.
        out.writeUtf8CodePoint(codePoint);
      }
    }
  }
}