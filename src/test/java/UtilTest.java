import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static top.doperj.util.security.Encoder.md5Encode;

public class UtilTest {
    @Test
    public void testMD5() {
        String hash = "35454B055CC325EA1AF2126E27707052";
        String password = "ILoveJava";
        assertEquals(hash, md5Encode(password));
    }
}
