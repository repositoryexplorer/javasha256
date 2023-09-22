package com.alfanet.javasha256;

import com.alfanet.javasha256.hkdf.HkdfWrapper;
import com.alfanet.javasha256.parameters.ParametersParser;
import com.alfanet.javasha256.sha.ShaWrapper;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

public class MainRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments args) throws Exception {
        ParametersParser params = new ParametersParser();
        if (!params.parseArguments(args)) {
            System.exit(0);
        } else {
            if (params.isHKDF()) {
                HkdfWrapper.calculateKey(params.getKeyFilePath(), params.getOutputFilePath());
            } else if (params.isSha256()) {
                ShaWrapper.calculateFileHash(params.getInputFilePath(), params.getOutputFilePath());
            }
        }
    }
}
