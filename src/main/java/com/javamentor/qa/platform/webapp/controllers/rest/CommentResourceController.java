package com.javamentor.qa.platform.webapp.controllers.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "CommentResourceController", description = "Позволяет работать с комментариями")
@RestController
@RequestMapping("api/user/comment")
public class CommentResourceController {

}
