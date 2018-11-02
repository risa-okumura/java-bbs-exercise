package jp.co.rakus.form;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 記事情報のフォームクラス.
 * 
 * @author risa.okumura
 *
 */
public class ArticleForm {
	
	/**　投稿者名	 */
	@NotBlank(message="名前は必須です")
	@Size(max=50, message="名前は50文字以内で入力してください")
	private String name;
	/**　投稿内容	 */
	@NotBlank(message="投稿内容を入力してください。")
	private String content;
	
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
