package com.yupi.yuoj.judge.strategy;

import com.yupi.yuoj.judge.codeSandBox.model.JudgeInfo;

/**
 * 判题策略
 */
public interface JudgeStrategy {

    /**
     * 判题策略
     *
     * @param judgeContext
     * @return
     */
    JudgeInfo doJudge(JudgeContext judgeContext);
}
