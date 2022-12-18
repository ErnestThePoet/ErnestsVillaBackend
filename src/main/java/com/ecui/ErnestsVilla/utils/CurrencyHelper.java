package com.ecui.ErnestsVilla.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class CurrencyHelper {
    private static final DecimalFormat format = new DecimalFormat("0.00");

    public static String getYuanFromCents(int cents) {
        return format.format(
                new BigDecimal(cents).divide(new BigDecimal(100)));
    }

    public static int getCentsFromYuan(String yuan) {
        return new BigDecimal(yuan).multiply(new BigDecimal(100)).intValueExact();
    }

    public static int getItemTotalPriceYuan(String priceYuan, int count) {
        return getCentsFromYuan(priceYuan) * count;
    }
}
