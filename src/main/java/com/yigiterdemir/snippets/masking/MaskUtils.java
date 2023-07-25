package com.yigiterdemir.snippets.masking;

import org.apache.commons.lang3.StringUtils;

public final class MaskUtils {

    private MaskUtils() {
    }

    public static String maskCardNumber(String cardNumber) {
        return mask(cardNumber, 6, 4);
    }

    public static String mask(String cardNumber, int showFirstDigitCount, int showLastDigitCount) {
        String leftPart = StringUtils.left(cardNumber, showFirstDigitCount);
        String maskedPart = StringUtils.repeat("*", cardNumber.length() - (showFirstDigitCount + showLastDigitCount));
        String rightPart = StringUtils.right(cardNumber, showLastDigitCount);
        return leftPart + maskedPart + rightPart;
    }

}