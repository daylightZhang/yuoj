package com.yupi.yuoj.judge.codeSandBox;

import com.yupi.yuoj.judge.codeSandBox.impl.ExampleCodeSandbox;
import com.yupi.yuoj.judge.codeSandBox.impl.RemoteCodeSandbox;
import com.yupi.yuoj.judge.codeSandBox.impl.ThirdPartyCodeSandbox;

/**
 * 代码沙箱工厂类
 */
public class CodeSandboxFactory {

    /**
     * 生成代码沙箱实例
     * @param type
     * @return
     */
    public static CodeSandbox newInstance(String type) {
        switch (type) {
            case "example":
                return new ExampleCodeSandbox();
            case "thirdParty":
                return new ThirdPartyCodeSandbox();
            case "remote":
                return new RemoteCodeSandbox();
            default:
                return new ExampleCodeSandbox();
        }
    }
}
