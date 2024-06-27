package ghojeong.auth.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

// NOTE: https://www.baeldung.com/sha-256-hashing-java
public final class SHA256Util {
    public static boolean comparePassword(String authPassword, String dbPassword) {
        return SHA256Util.encrypt(authPassword).equals(dbPassword);
    }

    public static String encrypt(String str) {
        if (str == null) {
            return null;
        }
        MessageDigest digest = getDigest();
        digest.update(str.getBytes(StandardCharsets.UTF_8));
        return bytesToHex(digest.digest());
    }

    private static MessageDigest getDigest() {
        try {
            return MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder builder = new StringBuilder();
        for (byte b : bytes) {
            builder.append(String.format("%02x", b));
        }
        return builder.toString();
    }
}
