package com.yupi.yuoj.judge.strategy;

import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.judge.codeSandBox.model.JudgeInfo;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import lombok.Data;

import java.util.List;

/**
 * 用于定义策略中传递的参数
 */
@Data
public class JudgeContext {

    /**
     * 题目要求
     */
    private JudgeInfo judgeInfo;

    /**
     * 输入信息
     */
    private List<String> inputList;

    /**
     * 输出结果
     */
    private List<String> outputList;

    private List<JudgeCase> judgeCases;

    private Question question;

    private QuestionSubmit questionSubmit;
}
