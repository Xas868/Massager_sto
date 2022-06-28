package com.javamentor.qa.platform.service.impl.model;

import com.javamentor.qa.platform.dao.impl.model.model.CommentQuestionDao;
import com.javamentor.qa.platform.models.entity.question.CommentQuestion;
import com.javamentor.qa.platform.service.abstracts.model.CommentQuestionService;
import org.springframework.stereotype.Service;


@Service
public class CommentQuestionServiceImpl extends ReadWriteServiceImpl<CommentQuestion, Long> implements CommentQuestionService {

    private final CommentQuestionDao commentDao;

    public CommentQuestionServiceImpl(CommentQuestionDao commentDao) {
        super(commentDao);
        this.commentDao = commentDao;
    }

}
