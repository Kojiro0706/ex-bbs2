package com.example.repository;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import com.example.domain.Article;
import com.example.domain.Comment;

public class ArticleRepository {
	@Autowired
	private NamedParameterJdbcTemplate template;

	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {

		// 記事一覧が入るarticleListを生成
		List<Article> articleList = new LinkedList<Article>();
		List<Comment> commentList = null;

		// 前の行の記事IDを退避しておく変数
		int beforeArticleId = 0;

		while (rs.next()) {
			// 現在検索されている記事IDを退避
			int nowArticleId = rs.getInt("id");

			// 現在の記事IDと前の記事IDが違う場合はArticleオブジェクトを生成
			if (nowArticleId != beforeArticleId) {
				Article article = new Article();
				article.setId(nowArticleId);
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				// 空のコメントリストを作成しArticleオブジェクトにセットしておく
				commentList = new ArrayList<Comment>();
				article.setCommentList(commentList);
				// コメントがセットされていない状態のArticleオブジェクトをarticleListオブジェクトにadd
				articleList.add(article);
			}

			// 記事だけあってコメントがない場合はCommentオブジェクトは作らない
			if (rs.getInt("com_id") != 0) {
				Comment comment = new Comment();
				comment.setId(rs.getInt("com_id"));
				comment.setName(rs.getString("com_name"));
				comment.setContent(rs.getString("com_content"));
				// コメントをarticleオブジェクト内にセットされているcommentListに直接addしている(参照型なのでこのようなことができる)
				commentList.add(comment);
			}

			// 現在の記事IDを前の記事IDを入れる退避IDに格納
			beforeArticleId = nowArticleId;
		}
		return articleList;

	};

//	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = new BeanPropertyRowMapper<>(Article.class);

	/**
	 * 全件検索をおこなう.
	 * 
	 * @return 記事情報
	 */

	public List<Article> findAll() {
		String sql = "SELECT a.id, a.name, a.content,c.id AS com_id,c.name AS com_name,c.content AS com_content,c.article_id FROM articles AS a "
				+ "LEFT JOIN comments AS c ON a.id = c.article_id ORDER BY a.id DESC";

		List<Article> articleList = template.query(sql, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;
	}

//	public List<Article> findAll() {
//		String sql = "SELECT id, name, content FROM articles ORDER BY id DESC";
//
//		List<Article> articleList = template.query(sql, ARTICLE_ROW_MAPPER);
//
//		return articleList;
//	}

	/**
	 * 記事情報を挿入する.
	 * 
	 * @param article 記事情報
	 */
	public void insert(Article article) {
		SqlParameterSource param = new BeanPropertySqlParameterSource(article);
		String sql = "INSERT INTO articles(name, content)VALUES(:name,:content)";
		template.update(sql, param);
	}

	/**
	 * 記事情報を削除する.
	 * 
	 * @param id ID
	 */
	public void deletById(int id) {

		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		String sql = "DELETE FROM articles WHERE id = :id";
		template.update(sql, param);

	}
}
