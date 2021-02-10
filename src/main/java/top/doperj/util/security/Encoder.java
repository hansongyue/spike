package top.doperj.util.security;

import top.doperj.util.exception.InternalServerException;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class Encoder {
    static {
        instance = new Encoder();
    }

    private Encoder() {
        Random rand = new Random();
        Encoder.salt = rand.nextLong();
    }

    private static Encoder instance;

    private static long salt;

    public static long getSalt() {
        return salt;
    }

    public static Encoder getInstance() {
        return instance;
    }

    public static String md5Encode(String text) {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new InternalServerException("unable to perform MD5 encode");
        }
        md.update(text.getBytes());
        byte[] digest = md.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }
}
