package jp.co.rakus.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Article;
import jp.co.rakus.domain.Comment;

/**
 * 記事の情報の入ったテーブルを操作するリポジトリ.
 * 
 * @author risa.okumura
 *
 */
@Repository
public class ArticleRepository {

	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs, i) -> {

		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));

		return article;
	};
	
	/** ResultSetExtractorを使ってまとめてデータを取り出す方法.*/
	private static final ResultSetExtractor<List<Article>> ARTICLE_RESULT_SET_EXTRACTOR = (rs) -> {
		List<Article> articleList = new ArrayList<>();
		Comment comment;
		List<Comment> commentList = new ArrayList<>();
		Integer lastArticleId = 0;
		Article article = null;
		while (rs.next()) {

			/** rsで取り出したarticleのIDが前回取り出したarticleのIDが異なるとき、新規にarticleを作成する.*/
			/** IDが同じだった場合は、新たにarticleは作成せず、そのarticleが持つcommentのみを追加する.*/
			/** commentは一行ずつ必要なので毎回インスタンス化しなければならない.*/
			if (lastArticleId != rs.getInt("id")) {

				article = new Article();
				article.setId(rs.getInt("id"));
				article.setName(rs.getString("name"));
				article.setContent(rs.getString("content"));
				articleList.add(article);
				lastArticleId = article.getId();

				commentList = new ArrayList<>();
				article.setCommentList(commentList);
			}
			if(rs.getInt("com_id") > 0) {
			comment = new Comment();
			comment.setId(rs.getInt("com_id"));
			comment.setName(rs.getString("com_name"));
			comment.setContent(rs.getString("com_content"));
			comment.setArticleId(rs.getInt("article_id"));
			commentList.add(comment);
			}
		}

		return articleList;
	};

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * 記事の全件検索を行う.
	 * 
	 * @return 記事情報一覧
	 */
	public List<Article> findAll() {
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Article> articleList = template.query(sql, param, ARTICLE_ROW_MAPPER);

		return articleList;

	}

	/**
	 * （中級）記事とコメント両方を一回のSQLで検索する.
	 * 
	 * @return 記事及びコメント情報一覧
	 */
	public List<Article> findAll2() {

		String sql = "SELECT a.id,a.name,a.content,c.id AS com_id,c.name AS com_name,c.content AS com_content,c.article_id FROM articles a LEFT OUTER JOIN comments c ON a.id = c.article_id ORDER BY a.id DESC;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Article> articleList = template.query(sql, param, ARTICLE_RESULT_SET_EXTRACTOR);

		return articleList;

	}

	/**
	 * 記事の投稿を行う.
	 * 
	 * @param article
	 *            投稿用の記事情報
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO articles(name, content) VALUES ( :name, :content ) ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content",
				article.getContent());
		template.update(sql, param);

	}

	/**
	 * 記事の削除を行う.
	 * 
	 * @param id
	 *            記事ID
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);

	}
	
	/**
	 * 記事、コメントを一括で削除する.
	 * 
	 * @param id  記事ID
	 *            
	 */
	public void deleteAllByArticleId(int id) {
		String sql = "WITH deleted AS (DELETE FROM articles WHERE id = :id RETURNING id) DELETE FROM comments WHERE article_id IN (SELECT id FROM deleted);";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
	}

}
