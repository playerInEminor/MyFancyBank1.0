import java.util.Currency;
import java.util.Locale;

/**
 * currency types supported in this bank
 */
public enum CurrencyType
{
    EUR,
    USD,
    CNY,
    JPY;

    public static Currency getCurrencyType(CurrencyType type)
    {
        switch (type)
        {
            case EUR: return Currency.getInstance(Locale.FRANCE);
            case USD: return Currency.getInstance(Locale.US);
            case CNY: return Currency.getInstance(Locale.CHINA);
            case JPY: return Currency.getInstance(Locale.JAPAN);
            default: return Currency.getInstance(Locale.US);
        }
    }
}
