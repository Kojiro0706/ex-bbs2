package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

public class DeleteArticleController {

	@Controller
	@RequestMapping("/article")
	public class ArticleController {

		@Autowired
		private ArticleRepository articleRepository;

		@Autowired
		private CommentRepository commentRepository;

		@RequestMapping("/deleteArticle")
		public String deleteArticle(String articleId) {

			int deleteId = Integer.parseInt(articleId);
			commentRepository.deleteByArticleId(deleteId);
			articleRepository.deletById(deleteId);

			return "redirect:/article";

		}
	}
}
