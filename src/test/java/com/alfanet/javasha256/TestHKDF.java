package com.alfanet.javasha256;

import com.alfanet.javasha256.hkdf.HkdfUtils;
import com.alfanet.javasha256.utils.Utils;
import org.junit.jupiter.api.Test;

import static com.alfanet.javasha256.utils.Utils.hexStringToByteArray;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestHKDF {

    @Test
    public void calculateHKDF_Test1() {
        String key = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
        String salt = "000102030405060708090a0b0c";
        int length = 42;
        String info = "f0f1f2f3f4f5f6f7f8f9";
        //PRK  = 0x077709362c2e32df0ddc3f0dc47bba6390b6c73bb50f9c3122ec844ad7c2b3e5 (32 octets)
        String OKM = "3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865";

        HkdfUtils hkdf = new HkdfUtils();
        byte[] calculatedKey = hkdf.calculateKey(hexStringToByteArray(key), hexStringToByteArray(salt), length, hexStringToByteArray(info));

        String result = Utils.bytesToString(calculatedKey);
        assertEquals(OKM, result);
        System.out.println(OKM);
        System.out.println(result);
    }

    @Test
    public void calculateHKDF_Test2() {
        String key = "000102030405060708090a0b0c0d0e0f101112131415161718191a1b1c1d1e1f202122232425262728292a2b2c2d2e2f303132333435363738393a3b3c3d3e3f404142434445464748494a4b4c4d4e4f";
        String salt = "606162636465666768696a6b6c6d6e6f707172737475767778797a7b7c7d7e7f808182838485868788898a8b8c8d8e8f909192939495969798999a9b9c9d9e9fa0a1a2a3a4a5a6a7a8a9aaabacadaeaf";
        int length = 82;
        String info = "b0b1b2b3b4b5b6b7b8b9babbbcbdbebfc0c1c2c3c4c5c6c7c8c9cacbcccdcecfd0d1d2d3d4d5d6d7d8d9dadbdcdddedfe0e1e2e3e4e5e6e7e8e9eaebecedeeeff0f1f2f3f4f5f6f7f8f9fafbfcfdfeff";
        //PRK  = 0x06a6b88c5853361a06104c9ceb35b45cef760014904671014a193f40c15fc244
        String OKM = "b11e398dc80327a1c8e7f78c596a49344f012eda2d4efad8a050cc4c19afa97c59045a99cac7827271cb41c65e590e09da3275600c2f09b8367793a9aca3db71cc30c58179ec3e87c14c01d5c1f3434f1d87";

        HkdfUtils hkdf = new HkdfUtils();
        byte[] calculatedKey = hkdf.calculateKey(hexStringToByteArray(key), hexStringToByteArray(salt), length, hexStringToByteArray(info));

        String result = Utils.bytesToString(calculatedKey);
        assertEquals(OKM, result);
        System.out.println(OKM);
        System.out.println(result);
    }

    @Test
    public void calculateHKDF_Test3() {
        String key = "0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b";
        String salt = "";
        int length = 42;
        String info = "";
        //PRK  = 0x19ef24a32c717b167f33a91d6f648bdf96596776afdb6377ac434c1c293ccb04
        String OKM = "8da4e775a563c18f715f802a063c5a31b8a11f5c5ee1879ec3454e5f3c738d2d9d201395faa4b61a96c8";

        HkdfUtils hkdf = new HkdfUtils();
        byte[] calculatedKey = hkdf.calculateKey(hexStringToByteArray(key), hexStringToByteArray(salt), length, hexStringToByteArray(info));

        String result = Utils.bytesToString(calculatedKey);
        assertEquals(OKM, result);
        System.out.println(OKM);
        System.out.println(result);
    }
}
