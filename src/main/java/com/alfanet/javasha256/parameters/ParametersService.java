package com.alfanet.javasha256.parameters;

import org.springframework.stereotype.Component;

@Component
public class ParametersService {
    public void printOptions() {
        System.out.println("Usage:");
        System.out.println(" javasha256  file");
        System.out.println("Calculates SHA256 sum of the file. If file is not valid path, it is treated as input of sha256 function.");
    }
}
