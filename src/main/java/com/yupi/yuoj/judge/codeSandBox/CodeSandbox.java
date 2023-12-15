package com.yupi.yuoj.judge.codeSandBox;

import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeResponse;

/**
 * 代码沙箱接口定义
 */
public interface CodeSandbox {

    /**
     * 执行代码
     *
     * @param executeCodeRequest
     * @return
     */
    ExecuteCodeResponse execute(ExecuteCodeRequest executeCodeRequest);
}
