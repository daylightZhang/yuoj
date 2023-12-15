package com.yupi.yuoj.judge.codeSandBox;

import com.yupi.yuoj.judge.codeSandBox.impl.ExampleCodeSandbox;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.yupi.yuoj.model.enums.QuestionSubmitLanguageEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
class CodeSandboxTest {

    @Value("${code-sandbox.type:example}")
    private String type;

    @Test
    void execute() {
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
        String code = "";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        ExecuteCodeResponse executeCodeResponse = codeSandBox.execute(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }

    @Test
    void executeByProxy() {
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
        String code = "";
        String language = QuestionSubmitLanguageEnum.JAVA.getValue();
        List<String> inputList = Arrays.asList("1 2", "3 4");
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.execute(executeCodeRequest);
        Assertions.assertNotNull(executeCodeResponse);
    }
}