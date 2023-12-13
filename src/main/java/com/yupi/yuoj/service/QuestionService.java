package com.yupi.yuoj.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.yuoj.model.dto.question.QuestionQueryRequest;
import com.yupi.yuoj.model.entity.Question;
import com.yupi.yuoj.model.entity.Question;
import com.baomidou.mybatisplus.extension.service.IService;
import com.yupi.yuoj.model.vo.QuestionVO;

import javax.servlet.http.HttpServletRequest;

/**
* @author jingkaizhang
* @description 针对表【question(题目)】的数据库操作Service
* @createDate 2023-12-13 16:16:48
*/
public interface QuestionService extends IService<Question> {
    /**
     * 校验
     *
     * @param post
     * @param add
     */
    void validQuestion(Question post, boolean add);

    /**
     * 获取查询条件
     *
     * @param postQueryRequest
     * @return
     */
    QueryWrapper<Question> getQueryWrapper(QuestionQueryRequest postQueryRequest);
    

    /**
     * 获取问题封装
     *
     * @param post
     * @param request
     * @return
     */
    QuestionVO getQuestionVO(Question post, HttpServletRequest request);

    /**
     * 分页获取问题封装
     *
     * @param postPage
     * @param request
     * @return
     */
    Page<QuestionVO> getQuestionVOPage(Page<Question> postPage, HttpServletRequest request);
}
