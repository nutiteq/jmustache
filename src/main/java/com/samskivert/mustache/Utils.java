package com.samskivert.mustache;

import java.util.Vector;

public class Utils {

    public static String textReplace(final String original, final String tokenToBeReplaced, final String value) {
        final StringBuffer result = new StringBuffer();
        final String[] originalSplit = split(original, tokenToBeReplaced);
        for (int i = 0; i < originalSplit.length; i++) {
            result.append(originalSplit[i]);
            if (i != originalSplit.length - 1) {
                result.append(value);
            }
        }
        return result.toString();
    }

    public static String[] split(final String string, final String splitBy) {
        final Vector tokens = new Vector();
        final int tokenLength = splitBy.length();

        int tokenStart = 0;
        int splitIndex;
        while ((splitIndex = string.indexOf(splitBy, tokenStart)) != -1) {
            tokens.addElement(string.substring(tokenStart, splitIndex));
            tokenStart = splitIndex + tokenLength;
        }

        tokens.addElement(string.substring(tokenStart));

        final String[] result = new String[tokens.size()];
        tokens.copyInto(result);
        return result;
    }
}
