package de.hska.vis.webshop.auth.userdetails;

import java.security.SecureRandom;
import java.util.Random;

public class SpecialUserDetails {
    private static final String symbols = "ABCDEFGJKLMNPRSTUVWXYZ0123456789";
    private static final Random random = new SecureRandom();
    private static final char[] buf = new char[32];

    private static String string;

    public static String getString() {
        if (string == null) {
            for (int idx = 0; idx < buf.length; ++idx)
                buf[idx] = symbols.charAt(random.nextInt(symbols.length()));
            return new String(buf);
        }
        return string;
    }
}
