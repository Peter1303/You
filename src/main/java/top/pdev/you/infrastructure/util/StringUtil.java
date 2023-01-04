package top.pdev.you.infrastructure.util;

import java.util.Locale;

/**
 * Created in 2022/5/26 15:17
 *
 * @author Peter1303
 */
public class StringUtil {
    public static String toLowercaseCamel(String string) {
        String target = string;
        target = target.substring(0, 1).toLowerCase(Locale.ROOT);
        return target + string.substring(1);
    }
}
