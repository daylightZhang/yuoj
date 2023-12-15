package com.yupi.yuoj.model.dto.questionSubmit;

import lombok.Data;

/**
 * 问题提交添加请求
 */
@Data
public class QuestionSubmitAddRequest {
    /**
     * 编程语言
     */
    private String language;

    /**
     * 用户代码
     */
    private String code;

    /**
     * 题目id
     */
    private Long questionId;

    private static final long serialVersionUID = 1L;

}
