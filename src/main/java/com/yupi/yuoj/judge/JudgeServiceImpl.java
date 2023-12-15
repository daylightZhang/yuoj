package com.yupi.yuoj.judge;

import cn.hutool.json.JSONUtil;
import com.yupi.yuoj.common.ErrorCode;
import com.yupi.yuoj.config.JsonConfig;
import com.yupi.yuoj.exception.BusinessException;
import com.yupi.yuoj.judge.codeSandBox.CodeSandbox;
import com.yupi.yuoj.judge.codeSandBox.CodeSandboxFactory;
import com.yupi.yuoj.judge.codeSandBox.CodeSandboxProxy;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeRequest;
import com.yupi.yuoj.judge.codeSandBox.model.ExecuteCodeResponse;
import com.yupi.yuoj.judge.strategy.DefaultJudgeStrategy;
import com.yupi.yuoj.judge.strategy.JudgeContext;
import com.yupi.yuoj.model.dto.question.JudgeCase;
import com.yupi.yuoj.model.dto.question.JudgeConfig;
import com.yupi.yuoj.model.dto.questionSubmit.JudgeInfo;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.QuestionSubmit;
import com.yupi.yuoj.model.enums.JudgeInfoMessageEnum;
import com.yupi.yuoj.model.enums.QuestionSubmitLanguageEnum;
import com.yupi.yuoj.model.enums.QuestionSubmitStatusEnum;
import com.yupi.yuoj.model.vo.QuestionSubmitVO;
import com.yupi.yuoj.service.QuestionService;
import com.yupi.yuoj.service.QuestionSubmitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JudgeServiceImpl implements JudgeService {

    @Resource
    private QuestionService questionService;

    @Resource
    private QuestionSubmitService questionSubmitService;

    @Resource
    private JudgeManager judgeManager;

    @Value("${code-sandbox.type:example}")
    private String type;

    /**
     * 判题接口
     *
     * @param questionSubmitId
     * @return
     */
    @Override
    public QuestionSubmit doJudge(long questionSubmitId) {
        // 传入题目的提交，找到对应的题目，提交信息
        QuestionSubmit questionSubmit = questionSubmitService.getById(questionSubmitId);
        if (questionSubmit == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "提交信息不存在");
        }
        Long id = questionSubmit.getId();
        Question question = questionService.getById(id);
        if (question == null) {
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR, "题目不存在");
        }
        // 如果为等待状态
        if (questionSubmit.getStatus().equals(QuestionSubmitStatusEnum.WAITING.getValue())) {
            throw new BusinessException(ErrorCode.OPERATION_ERROR, "题目正在判题中");
        }
        // 修改题目为判题中的状态
        QuestionSubmit questionSubmitUpdate = new QuestionSubmit();
        questionSubmitUpdate.setId(questionSubmitId);
        questionSubmitUpdate.setStatus(QuestionSubmitStatusEnum.WAITING.getValue());
        boolean updateSuccess = questionSubmitService.updateById(questionSubmitUpdate);
        if (!updateSuccess) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        // 调用代码沙箱
        CodeSandbox codeSandBox = CodeSandboxFactory.newInstance(type);
        String language = questionSubmit.getLanguage();
        String code = questionSubmit.getCode();
        // 获取测试用例
        String judgeCaseStr = question.getJudgeCase();
        List<JudgeCase> judgeCases = JSONUtil.toList(judgeCaseStr, JudgeCase.class);
        List<String> inputList = judgeCases.stream().map(JudgeCase::getInput).collect(Collectors.toList());
        // 调用沙箱执行
        ExecuteCodeRequest executeCodeRequest = ExecuteCodeRequest.builder()
                .code(code)
                .language(language)
                .inputList(inputList)
                .build();
        CodeSandboxProxy codeSandboxProxy = new CodeSandboxProxy(codeSandBox);
        ExecuteCodeResponse executeCodeResponse = codeSandboxProxy.execute(executeCodeRequest);
        // 比较输出
        JudgeContext judgeContext = new JudgeContext();
        judgeContext.setQuestionSubmit(questionSubmit);
        judgeContext.setJudgeInfo(executeCodeResponse.getJudgeInfo());
        judgeContext.setInputList(inputList);
        judgeContext.setOutputList(executeCodeResponse.getOutputList());
        judgeContext.setJudgeCases(judgeCases);
        judgeContext.setQuestion(question);
        JudgeInfo judgeInfo = judgeManager.doJudge(judgeContext);
        // 修改状态
        QuestionSubmit questionSubmitUpdate1 = new QuestionSubmit();
        questionSubmitUpdate1.setId(questionSubmitId);
        questionSubmitUpdate1.setStatus(QuestionSubmitStatusEnum.SUCCEED.getValue());
        questionSubmitUpdate1.setJudgeInfo(JSONUtil.toJsonStr(judgeInfo));
        boolean updateSuccess2 = questionSubmitService.updateById(questionSubmitUpdate1);
        if (!updateSuccess2) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "题目状态更新错误");
        }
        return questionSubmitService.getById(questionSubmitId);
    }
}
