package com.alfanet.javasha256;

import com.alfanet.javasha256.parameters.ParametersParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.ApplicationArguments;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParametersTest {

    private ApplicationArguments args;

    @BeforeEach
    public void beforeEach() {
        args = mock(ApplicationArguments.class);
    }


    @Test
    void testParameters() {
        List<String> listOfNonOpts = new ArrayList<>();
        listOfNonOpts.add("inputfile");
        Set<String> setOfOpts = new HashSet<>();
        setOfOpts.add("o");
        setOfOpts.add("k");
        setOfOpts.add("a");
        when(args.getNonOptionArgs()).then(a -> listOfNonOpts);

        when(args.getOptionNames()).then(a -> setOfOpts);
        when(args.getOptionValues(anyString())).then(new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                String paramName = invocation.getArgument(0);
                List<String> result = new ArrayList<>();
                switch (paramName) {
                    case "o":
                        result.add("outputfile");
                        break;
                    case "k":
                        result.add("keyfile");
                        break;
                    case "a":
                        result.add("sha256");
                        break;
                }
                return result;
            }
        });
        ParametersParser parametersParser = new ParametersParser();
        parametersParser.parseArguments(args);
        assert (parametersParser.getInputFilePath().equals("inputfile"));
        assertNull(parametersParser.getKeyFilePath());
        assert (parametersParser.getOutputFilePath().isPresent() && parametersParser.getOutputFilePath().get().equals("outputfile"));
        assertEquals(parametersParser.getAlgorithm(), ParametersParser.Algorithm.SHA256);
    }

    @Test
    void testShaWithoutInputFile() {
        List<String> listOfNonOpts = new ArrayList<>();
        Set<String> setOfOpts = new HashSet<>();
        setOfOpts.add("o");
        setOfOpts.add("a");
        when(args.getNonOptionArgs()).then(a -> listOfNonOpts);

        when(args.getOptionNames()).then(a -> setOfOpts);
        when(args.getOptionValues(anyString())).then(new Answer<List<String>>() {
            @Override
            public List<String> answer(InvocationOnMock invocation) throws Throwable {
                String paramName = invocation.getArgument(0);
                List<String> result = new ArrayList<>();
                switch (paramName) {
                    case "o":
                        result.add("outputfile");
                        break;
                    case "a":
                        result.add("sha256");
                        break;
                }
                return result;
            }
        });
        ParametersParser parametersParser = new ParametersParser();
        parametersParser.parseArguments(args);
        assertNull(parametersParser.getInputFilePath());
        assertNull(parametersParser.getKeyFilePath());
        assertEquals(parametersParser.getAlgorithm(), ParametersParser.Algorithm.SHA256);
    }
}
