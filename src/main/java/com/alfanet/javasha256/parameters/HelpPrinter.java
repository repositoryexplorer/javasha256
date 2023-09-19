package com.alfanet.javasha256.parameters;

public class HelpPrinter {
    public static void printHelp() {
        System.out.println("Usage:");
        System.out.println(" java -jar javasha256.jar [options] [file]");
        System.out.println("Calculates SHA256 sum of a file or creates keying material based on HKDF-SHA256.");
        System.out.println("Options");
        System.out.println(" --a      algorithm to use, available values: \"sha256\" or \"hkdf\", default is \"sha256\"");
        System.out.println(" --k      file with key");
        System.out.println(" --o      optional, redirects result to a file");
        System.out.println("\n");
        System.out.println("When calculating key from hkdf, we have to provide key file. This file is JSON file with two fields: key, salt, length or the generated key and info - additonal, application specific information");
        System.out.println("Example key file:");
        System.out.println("""
                {
                    "key": "A0A0A0A0A0A0A0A",
                    "salt": "BFBFBFBFBFBFBF",
                    "length": 32,
                    "info": "FEFEFEFEFEFE"
                }
                """);
        System.out.println("Example usage");
        System.out.println(" java -jar javasha256.jar --a=sha256 file");
        System.out.println(" java -jar javasha256.jar --a=hkdf --k=keyfile --o=outputfile");
    }
}
