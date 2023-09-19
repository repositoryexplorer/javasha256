package com.alfanet.javasha256.parameters;

import lombok.Getter;
import org.springframework.boot.ApplicationArguments;

import java.util.List;
import java.util.Optional;

@Getter
public class ParametersParser {
    public enum Algorithm {SHA256, HKDF}

    private Algorithm algorithm;
    private String keyFilePath;
    private String saltFilePath;
    private Optional<String> outputFilePath;
    private String inputFilePath;


    public boolean parseArguments(ApplicationArguments params) {
        algorithm = getAlgorithm(params.getOptionValues("a"));
        if (algorithm == Algorithm.HKDF) {
            Optional<String> keyFile = getFileValue(params.getOptionValues("k"));
            if (keyFile.isPresent()) {
                keyFilePath = keyFile.get();
            } else {
                parsingFailed();
                return false;
            }
        } else if (algorithm == Algorithm.SHA256) {
            if (params.getNonOptionArgs().size() != 1) {
                parsingFailed();
                return false;
            } else {
                inputFilePath = params.getNonOptionArgs().get(0);
            }
        }

        outputFilePath = getFileValue(params.getOptionValues("o"));
        return true;
    }

    private Algorithm getAlgorithm(List<String> a) {
        if (a != null && a.size() == 1) {
            String paramValue = a.get(0).toUpperCase();
            if (paramValue.equals("SHA256") || paramValue.equals("HKDF")) return Algorithm.valueOf(paramValue);
            else return Algorithm.SHA256;
        } else return Algorithm.SHA256;
    }

    private Optional<String> getStringValue(List<String> paramValues) {
        if (paramValues != null && paramValues.size() > 0) {
            return Optional.of(paramValues.get(0));
        } else return Optional.empty();
    }

    private Optional<String> getFileValue(List<String> paramValues) {
        Optional<String> filePath = getStringValue(paramValues);
        if (filePath.isPresent()) {
            return filePath;
        } else return Optional.empty();
    }

    private void parsingFailed() {
        HelpPrinter.printHelp();
    }

    public boolean isHKDF() {
        return algorithm == Algorithm.HKDF;
    }

    public boolean isSha256() {
        return algorithm == Algorithm.SHA256;
    }
}
