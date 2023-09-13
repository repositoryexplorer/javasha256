package com.alfanet.javasha256;

import com.alfanet.javasha256.parameters.ParametersService;
import com.alfanet.javasha256.sha.ShaUtils;
import com.alfanet.javasha256.sha.sha256;
import com.alfanet.javasha256.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.time.Duration;
import java.time.LocalTime;

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
        LocalTime start = LocalTime.now();
        ShaUtils sha = new ShaUtils();
        FileInputStream inStream = new FileInputStream(path);

//        int zerosLength = (int) (64 - (Math.abs((64 - 63 - 9) % 64)));
//        System.out.println("ZEROS: " + zerosLength);
        byte[] allBytes = Utils.getPadding(inStream.readAllBytes());
        byte[] blockBytes = new byte[64];
        int blockCount = allBytes.length / 64;
        for (int i = 0; i < blockCount; i++) {
            System.arraycopy(allBytes, i * 64, blockBytes, 0, 64);
            int[] blockWords = new int[16];
            for (int j = 0; j < 16; j++) {
                byte[] intBytes = new byte[4];
                System.arraycopy(allBytes, (i * 64) + (j * 4), intBytes, 0, 4);
                ByteBuffer buffer = ByteBuffer.wrap(intBytes);
                buffer.order(ByteOrder.BIG_ENDIAN);
                blockWords[j] = buffer.getInt();
            }
            sha.calculateSha256(blockWords);
        }

        LocalTime end = LocalTime.now();
        Duration d = Duration.between(start, end);
        System.out.println("Czas : " + d.getSeconds() + "sec");
        System.out.println("Hash: " + sha.getHashString());
        inStream.close();


        FileInputStream inStream2 = new FileInputStream(path);
        LocalTime start2 = LocalTime.now();
        byte[] all = inStream2.readAllBytes();
        inStream2.close();
        byte[] h = sha256.hash(all);
        LocalTime end2 = LocalTime.now();
        Duration d2 = Duration.between(start2, end2);
        System.out.println("Czas : " + d2.getSeconds() + "sec");
        String s = "";
        for (int i = 0; i < h.length; i++) {
            s += String.format("%02x", h[i]);
            // if (s.length()==1) s
        }
        System.out.println("Hash: " + s);
    }
}
