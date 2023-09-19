package com.alfanet.javasha256.hkdf;

import com.alfanet.javasha256.utils.Utils;
import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Optional;

import static com.alfanet.javasha256.utils.Utils.hexStringToByteArray;

public class HkdfWrapper {
    @Data
    @AllArgsConstructor
    public static class KeyInput {
        String key;
        String salt;
        int length;
        String info;
    }

    public static void calculateKey(String keyFilePath, Optional<String> outputFilePath) {
        try {
            KeyInput keyInput = parseKeyFile(keyFilePath);

            HkdfUtils hkdf = new HkdfUtils();
            byte[] calculatedKey = hkdf.calculateKey(hexStringToByteArray(keyInput.key), hexStringToByteArray(keyInput.salt), keyInput.length, hexStringToByteArray(keyInput.info));

            if (outputFilePath.isPresent()) {
                Utils.writeToFile(Utils.bytesToString(calculatedKey), outputFilePath.get());
            } else {
                System.out.println("Key (" + calculatedKey.length + "bytes) :");
                System.out.println(Utils.bytesToString(calculatedKey));
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static KeyInput parseKeyFile(String keyFilePath) throws FileNotFoundException {
        Gson gson = new Gson();
        return gson.fromJson(new FileReader(keyFilePath), KeyInput.class);
    }
}
