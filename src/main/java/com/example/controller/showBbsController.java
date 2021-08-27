package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Article;
import com.example.repository.ArticleRepository;

@Controller
@RequestMapping("/showBbs")
public class showBbsController {

	@Autowired
	private ArticleRepository articleRepository;
	
	@RequestMapping("")
	public String showBbs(Model model) {
		List<Article> articleList = articleRepository.findAll();
		model.addAttribute("articleList", articleList);
		return "show-bbs";
	}
}
