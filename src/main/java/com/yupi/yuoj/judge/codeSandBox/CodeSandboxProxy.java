package com.yupi.yuoj.judge.codeSandBox;

import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CodeSandboxProxy implements CodeSandbox {

    private final CodeSandbox codeSandbox;

    public CodeSandboxProxy(CodeSandbox codeSandbox) {
        this.codeSandbox = codeSandbox;
    }

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    @Override
    public ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest) {
        log.info("代码沙箱请求信息:{}", executeCodeRequest.toString());
        ExecuteCodeResponse executeCodeResponse = codeSandbox.execute(executeCodeRequest);
        log.info("代码沙箱响应信息:{}", executeCodeResponse.toString());
        return executeCodeResponse;
    }
}
