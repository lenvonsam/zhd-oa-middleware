/* Copyright © 2010 www.myctu.cn. All rights reserved. */
/**
 * project : antlogistics
 * user created : pippo
 * date created : 2008-3-24 - 下午08:33:15
 */
package zhd.oa.middleware.utils;

/**
 * @since 2018/04-10
 * @author zhouxl
 */
public final class HexUtils {
    
    public static String toHexString(byte b[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            sb.append(HEX_CHARS.charAt(b[i] >>> 4 & 0xf));
            sb.append(HEX_CHARS.charAt(b[i] & 0xf));
        }

        return sb.toString();
    }

    public static byte[] toByteArray(String s) {
        byte buf[] = new byte[s.length() / 2];
        int j = 0;
        for (int i = 0; i < buf.length; i++)
            buf[i] = (byte) (Character.digit(s.charAt(j++), 16) << 4 | Character.digit(s.charAt(j++), 16));

        return buf;
    }

    private static final String HEX_CHARS = "0123456789abcdef";

}
