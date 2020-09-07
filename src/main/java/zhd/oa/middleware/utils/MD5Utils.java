/* Copyright © 2010 www.myctu.cn. All rights reserved. */
/**
 * project : antlogistics
 * user created : pippo
 * date created : 2008-3-24 - 下午08:24:01
 */
package zhd.oa.middleware.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @since 2018-3-24
 * @author zhouxl
 */
public final class MD5Utils {

    static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

    }

    public static byte[] md5(byte data[]) {
        return getDigest().digest(data);
    }

    public static byte[] md5(String data) {
        return md5(data.getBytes());
    }

    public static String md5Hex(byte data[]) {
        return HexUtils.toHexString(md5(data));
    }

    public static String md5Hex(String data) {
        return HexUtils.toHexString(md5(data));
    }

}
