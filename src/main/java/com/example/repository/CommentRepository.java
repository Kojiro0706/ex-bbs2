package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Comment;

/**
 * コメント情報を操作するリポジトリ.
 * 
 * @author hayato.saishu
 *
 */
@Repository
public class CommentRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;
	
	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) ->{
		Comment comment = new Comment();
		
		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));
		
		return comment;
	};
	
	/**
	 * 記事IDからコメントを取得する.
	 * 
	 * @param articleId 記事ID
	 * @return　コメントが入ったリスト
	 */
	public List<Comment> findByArticleId(Integer articleId){
		
		String sql = "SELECT id, name, content, article_id FROM comments WHERE article_id=:articleId ORDER BY id DESC;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		List<Comment> commentList =  template.query(sql, param, COMMENT_ROW_MAPPER);
		
		return commentList;
	}
	
	/**
	 * コメントを投稿する.
	 * 
	 * @param comment コメント情報のドメイン
	 */
	public void insert(Comment comment) {
		
		String sql = "INSERT INTO comments (name, content, article_id) VALUES (:name, :content, :articleId);";
		
		SqlParameterSource param = new BeanPropertySqlParameterSource(comment);
		
		template.update(sql, param);
	}
	
	public void deleteByArticleId(Integer articleId) {
		
		String sql = "DELETE FROM comments WHERE article_id=:articleId;";
		
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		
		template.update(sql, param);
	}
}
