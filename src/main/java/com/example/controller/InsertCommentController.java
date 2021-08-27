package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Comment;
import com.example.form.CommentForm;
import com.example.repository.CommentRepository;

@Controller
@RequestMapping("/insert-comment")
public class InsertCommentController {
	
	@Autowired
	private CommentRepository commentRepository;

	@RequestMapping("")
	public String insertComment(CommentForm form) {
		Comment comment = new Comment();
		BeanUtils.copyProperties(form, comment);
		commentRepository.insert(comment);
		return "redirect:/showBbs";
	}
}
