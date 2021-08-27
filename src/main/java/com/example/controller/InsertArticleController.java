package com.example.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.domain.Comment;
import com.example.form.ArticleForm;
import com.example.repository.ArticleRepository;
import com.example.repository.CommentRepository;

public class InsertArticleController {

	@Controller
	@RequestMapping("/article")
	public class ArticleController {

		@Autowired
		private ArticleRepository articleRepository;

		@Autowired
		private CommentRepository commentRepository;
		
		
		@RequestMapping("/insertArticle")
		public String insertArticle(ArticleForm form) {
			Article article = new Article();
			BeanUtils.copyProperties(form, article);
			articleRepository.insert(article);

			return "redirect:/article";
		}
	}

}
