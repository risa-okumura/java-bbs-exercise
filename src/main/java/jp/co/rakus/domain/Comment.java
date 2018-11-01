package jp.co.rakus.domain;

/**
 * コメントの情報を表すドメイン.
 * 
 * @author risa.okumura
 *
 */
public class Comment {
	
	/**	コメントのid */
	private Integer id;
	/**	投稿者名 */
	private String name;
	/** 書き込み内容 */
	private String content;
	/** 書き込んだ記事のid */
	private Integer articleId;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
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
	public Integer getArticleId() {
		return articleId;
	}
	public void setArticleId(Integer articleId) {
		this.articleId = articleId;
	}
	@Override
	public String toString() {
		return "Comment [id=" + id + ", name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
	}
	
	

}
