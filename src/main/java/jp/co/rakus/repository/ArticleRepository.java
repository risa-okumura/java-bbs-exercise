package jp.co.rakus.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import jp.co.rakus.domain.Article;

/**
 * 記事の情報の入ったテーブルを操作するリポジトリ.
 * 
 * @author risa.okumura
 *
 */
@Repository
public class ArticleRepository {
	
	private static final RowMapper<Article> ARTICLE_ROW_MAPPER = (rs,i) ->{
	
		Article article = new Article();
		article.setId(rs.getInt("id"));
		article.setName(rs.getString("name"));
		article.setContent(rs.getString("content"));
		
		return article;
	};
	
	@Autowired
	private NamedParameterJdbcTemplate template;
	
	/**
	 * 記事の全件検索を行う.
	 * 
	 * @return 記事情報一覧
	 */
	public List<Article> findAll(){
		String sql = "SELECT id,name,content FROM articles ORDER BY id DESC;";
		SqlParameterSource param = new MapSqlParameterSource();
		List<Article> articleList = template.query(sql, param, ARTICLE_ROW_MAPPER);
		
		return articleList;
		
	}
	
	/**
	 * 記事の投稿を行う.
	 * 
	 * @param article 投稿用の記事情報
	 */
	public void insert(Article article) {
		String sql = "INSERT INTO articles(name, content) VALUES ( :name, :content ) ;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", article.getName()).addValue("content", article.getContent());
		template.update(sql, param);
		
	}
	/**
	 * 記事の削除を行う.
	 * 
	 * @param id 記事ID
	 */
	public void deleteById(int id) {
		String sql = "DELETE FROM articles WHERE id=:id;";
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		template.update(sql, param);
		

		
		
	}
	

}
