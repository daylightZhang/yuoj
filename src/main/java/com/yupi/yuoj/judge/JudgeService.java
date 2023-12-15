package com.yupi.yuoj.judge;

import com.yupi.yuoj.model.entity.QuestionSubmit;
import com.yupi.yuoj.model.vo.QuestionSubmitVO;

public interface JudgeService {

    /**
     * 判题接口
     *
     * @param questionSubmitId
     * @return
     */
    QuestionSubmit doJudge(long questionSubmitId);
}
