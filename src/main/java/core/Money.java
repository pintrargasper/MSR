package core;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

public class Money {

    public static String format(float money) {
        NumberFormat numberFormat = NumberFormat.getCurrencyInstance();
        numberFormat.setCurrency(Currency.getInstance(Locale.GERMANY));
        return numberFormat.format(new BigDecimal(money));
    }
}