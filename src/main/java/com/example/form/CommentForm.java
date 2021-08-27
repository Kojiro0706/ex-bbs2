package com.example.form;

import javax.validation.constraints.NotBlank;

/**
 * コメント投稿の際のリクエストパラメータを受け取るフォーム.
 * 
 * @author hayato.saishu
 *
 */
public class CommentForm {

	/** 記事ID */
	private Integer articleId;
	/** コメント者名 */
	@NotBlank(message="名前を入力してください")
	private String name;
	/** コメント内容 */
	@NotBlank(message="内容を入力してください")
	private String content;

	public CommentForm() {
	}

	public CommentForm(Integer articleId, String name, String content) {
		super();
		this.articleId = articleId;
		this.name = name;
		this.content = content;
	}

	public Integer getArticleId() {
		return articleId;
	}

	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public String toString() {
		return "CommentForm [articleId=" + articleId + ", name=" + name + ", content=" + content + "]";
	}

}
