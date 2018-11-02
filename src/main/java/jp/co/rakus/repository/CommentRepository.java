package jp.co.rakus.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Comment;

/**
 * コメントの情報が入ったテーブルを操作するリポジトリ.
 * 
 * @author risa.okumura
 *
 */
@Repository
public class CommentRepository {

	private static final RowMapper<Comment> COMMENT_ROW_MAPPER = (rs, i) -> {
		Comment comment = new Comment();

		comment.setId(rs.getInt("id"));
		comment.setName(rs.getString("name"));
		comment.setContent(rs.getString("content"));
		comment.setArticleId(rs.getInt("article_id"));

		return comment;

	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 *　記事毎の全コメントを検索を行う.
	 * 
	 * @param articleId　記事のID
	 * @return　記事ごとの全コメント
	 */
	public List<Comment> findByArticleId(int articleId) {
		
		String sql = "SELECT id,name,content,article_id FROM comments WHERE article_id=:articleId ORDER BY id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId", articleId);
		List<Comment> commentList = template.query(sql, param, COMMENT_ROW_MAPPER);
		
		return commentList;
	}
	
	/**
	 * コメントの投稿を行う.
	 * 
	 * @param comment　コメント
	 */
	public void insert(Comment comment) {
		String sql = "INSERT INTO comments(name, content, article_id) VALUES (:name, :content, :articleId);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", comment.getName()).addValue("content", comment.getContent()).addValue("articleId", comment.getArticleId());
		template.update(sql, param);
		
	}
	
	/**
	 *　記事ごとの全コメントを削除する.
	 * 
	 * @param articleId 記事のID
	 */
	public void deleteByArticleId(int articleId) {
		String sql ="DELETE FROM comments WHERE article_id=:articleId;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("articleId",articleId );
		template.update(sql, param);
		
	}

}
