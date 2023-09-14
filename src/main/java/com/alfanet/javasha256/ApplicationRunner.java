package com.alfanet.javasha256;

import com.alfanet.javasha256.parameters.ParametersService;
import com.alfanet.javasha256.sha.ShaUtils;
import com.alfanet.javasha256.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.FileInputStream;
import java.io.IOException;

public class ApplicationRunner implements CommandLineRunner {

    @Autowired
    public ParametersService parametersService;

    @Override
    public void run(String... args) throws Exception {
        if (args.length == 0) {
            parametersService.printOptions();
        } else {
            if (Utils.isFile(args[0])) {
                calculateFileHash(args[0]);
            }
        }
    }

    private void calculateFileHash(String path) throws IOException {
        try (FileInputStream inStream = new FileInputStream(path)) {

            ShaUtils sha = new ShaUtils();
            byte[] allBytes = Utils.getPadding(inStream.readAllBytes());
            int blockCount = allBytes.length / 64;
            for (int i = 0; i < blockCount; i++) {
                int[] blockWords = Utils.toBlockWords(allBytes, i);
                sha.calculateSha256(blockWords);
            }
            String hash = Utils.hashToString(sha.getHasValues());
            System.out.println("Hash: " + hash);
        }
    }
}
