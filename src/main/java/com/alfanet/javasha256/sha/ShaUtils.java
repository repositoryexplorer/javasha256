package com.alfanet.javasha256.sha;

public class ShaUtils {
    public static int[] K = {
            0x428a2f98, 0x71374491, 0xb5c0fbcf, 0xe9b5dba5,
            0x3956c25b, 0x59f111f1, 0x923f82a4, 0xab1c5ed5,
            0xd807aa98, 0x12835b01, 0x243185be, 0x550c7dc3,
            0x72be5d74, 0x80deb1fe, 0x9bdc06a7, 0xc19bf174,
            0xe49b69c1, 0xefbe4786, 0x0fc19dc6, 0x240ca1cc,
            0x2de92c6f, 0x4a7484aa, 0x5cb0a9dc, 0x76f988da,
            0x983e5152, 0xa831c66d, 0xb00327c8, 0xbf597fc7,
            0xc6e00bf3, 0xd5a79147, 0x06ca6351, 0x14292967,
            0x27b70a85, 0x2e1b2138, 0x4d2c6dfc, 0x53380d13,
            0x650a7354, 0x766a0abb, 0x81c2c92e, 0x92722c85,
            0xa2bfe8a1, 0xa81a664b, 0xc24b8b70, 0xc76c51a3,
            0xd192e819, 0xd6990624, 0xf40e3585, 0x106aa070,
            0x19a4c116, 0x1e376c08, 0x2748774c, 0x34b0bcb5,
            0x391c0cb3, 0x4ed8aa4a, 0x5b9cca4f, 0x682e6ff3,
            0x748f82ee, 0x78a5636f, 0x84c87814, 0x8cc70208,
            0x90befffa, 0xa4506ceb, 0xbef9a3f7, 0xc67178f2};

    private int H0 = 0x6a09e667;
    private int H1 = 0xbb67ae85;
    private int H2 = 0x3c6ef372;
    private int H3 = 0xa54ff53a;
    private int H4 = 0x510e527f;
    private int H5 = 0x9b05688c;
    private int H6 = 0x1f83d9ab;
    private int H7 = 0x5be0cd19;

    private int a;
    private int b;
    private int c;
    private int d;
    private int e;
    private int f;
    private int g;
    private int h;

    private int[] W = new int[64];

    public void calculateSha256(int[] blockWords) { // for sha256 input is 16 4-bytes integers
        //1) Schedule message
        System.arraycopy(blockWords, 0, W, 0, 16);
        for (byte t = 16; t < 64; t++) {
            W[t] = SSIG1(W[t - 2]) + W[t - 7] + SSIG0(W[t - 15]) + W[t - 16];
        }
        //2) Calculate variables
        a = H0;
        b = H1;
        c = H2;
        d = H3;
        e = H4;
        f = H5;
        g = H6;
        h = H7;
        for (byte t = 0; t < 64; t++) {
            int T1 = (h + BSIG1(e) + CH(e, f, g) + K[t] + W[t]);
            int T2 = (BSIG0(a) + MAJ(a, b, c));
            h = g;
            g = f;
            f = e;
            e = d + T1;
            d = c;
            c = b;
            b = a;
            a = T1 + T2;
        }
        //3) Calculate HASH
        H0 = a + H0;
        H1 = b + H1;
        H2 = c + H2;
        H3 = d + H3;
        H4 = e + H4;
        H5 = f + H5;
        H6 = g + H6;
        H7 = h + H7;
    }

    private int CH(int x, int y, int z) {
        return (x & y) ^ ((~x) & z);
    }

    private int MAJ(int x, int y, int z) {
        return (x & y) ^ (x & z) ^ (y & z);
    }

    private int BSIG0(int x) {
        return ROTR(x, 2) ^ ROTR(x, 13) ^ ROTR(x, 22);
    }

    private int BSIG1(int x) {
        return ROTR(x, 6) ^ ROTR(x, 11) ^ ROTR(x, 25);
    }

    private int SSIG0(int x) {
        return ROTR(x, 7) ^ ROTR(x, 18) ^ SHR(x, 3);
    }

    private int SSIG1(int x) {
        return ROTR(x, 17) ^ ROTR(x, 19) ^ SHR(x, 10);
    }

    private int SHR(int x, int n) {
        return x >>> n;
    }

    private int ROTR(int x, int n) {
        return Integer.rotateRight(x, n);
    }

    public int[] getHasValues() {
        int[] result = {H0, H1, H2, H3, H4, H5, H6, H7};
        return result;
    }
}
