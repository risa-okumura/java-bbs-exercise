package jp.co.rakus.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 	コメントのフォームクラス.
 * 
 * @author risa.okumura
 *
 */
public class CommentForm {
	
	/**	名前 */
	@NotBlank(message="名前は必須です")
	@Size(max=50, message="名前は50文字以内で入力してください")
	private String name;
	/**	コメント内容 */
	@NotBlank(message="コメント内容を入力してください")
	private String content;
	/**	記事ID */
	private String articleId;
	
	public Integer getIntArticleId() {
		return Integer.parseInt(this.articleId);
	}
	

	public String getArticleId() {
		return articleId;
	}
	public void setArticleId(String articleId) {
		this.articleId = articleId;
	}
	@Override
	public String toString() {
		return "CommentForm [name=" + name + ", content=" + content + ", articleId=" + articleId + "]";
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
	
	
	

}
