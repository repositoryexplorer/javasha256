package com.alfanet.javasha256;

import com.alfanet.javasha256.sha.ShaUtils;
import com.alfanet.javasha256.utils.Utils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class JavaSha256ApplicationTests {

    private String _64bytes = "1111111111111111222222222222222233333333333333334444444444444444";
    private String _64bytesHash = "bbcadfe152d23de1750546604ae19576e6b14842f2373afc192c45e18012487a";
    private String _32bytes = "11111111111111112222222222222222";

    private String _32bytesHash = "ea15625db9aa5aaa3adae590c666fa0eca541a662cf3b31665997596f8db253e";

    @Test
    void testPadding() {
        try {
            byte[] bytesWithPadding = Utils.getPadding(_64bytes.getBytes());
            assert (bytesWithPadding.length == 128);
            bytesWithPadding = Utils.getPadding(_32bytes.getBytes());
            assert (bytesWithPadding.length == 64);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void testSha64() throws IOException {
        ShaUtils sha = new ShaUtils();
        byte[] allBytes = Utils.getPadding(_64bytes.getBytes());
        int blockCount = allBytes.length / 64;
        for (int i = 0; i < blockCount; i++) {
            int[] blockWords = Utils.toBlockWords(allBytes, i);
            sha.calculateSha256(blockWords);
        }
        String hash = Utils.hashToString(sha.getHasValues());
        assert (_64bytesHash.equals(hash));
    }

    @Test
    void testSha32() throws IOException {
        ShaUtils sha = new ShaUtils();
        byte[] allBytes = Utils.getPadding(_32bytes.getBytes());
        int blockCount = allBytes.length / 64;
        for (int i = 0; i < blockCount; i++) {
            int[] blockWords = Utils.toBlockWords(allBytes, i);
            sha.calculateSha256(blockWords);
        }
        String hash = Utils.hashToString(sha.getHasValues());
        assert (_32bytesHash.equals(hash));
    }
}
