package jp.co.rakus.controller;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import jp.co.rakus.domain.Article;
import jp.co.rakus.domain.Comment;
import jp.co.rakus.form.ArticleForm;
import jp.co.rakus.form.CommentForm;
import jp.co.rakus.repository.ArticleRepository;
import jp.co.rakus.repository.CommentRepository;

/**
 * 掲示板を表示するコントローラー.
 * 
 * @author risa.okumura
 *
 */
@Controller
@RequestMapping("/article")
public class ArticleController {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CommentRepository commentRepository;

	@ModelAttribute
	public ArticleForm setUpArticleForm() {
		return new ArticleForm();
	}

	@ModelAttribute
	public CommentForm setUpCommentForm() {
		return new CommentForm();
	}
	

	/**
	 * 記事の一覧を表示する.
	 * 
	 * @return 記事一覧画面
	 */
	@RequestMapping("/index")
	public String index(Model model) {

		List<Article> articleList = articleRepository.findAll();
		for (Article article : articleList) {
			List<Comment> commentList = commentRepository.findByArticleId(article.getId());
			article.setCommentList(commentList);
			commentList.size();
		}
		model.addAttribute("articleList", articleList);
		return "/bbs";
	}

	/**
	 * 記事を投稿する.
	 * 
	 * @param articleForm 記事のフォーム
	 * @param model リクエストスコープ
	 * @param result 入力値チェック用
	 * @return 記事一覧画面
	 */
	@RequestMapping("/insertArticle")
	public String insertArticle(@Validated ArticleForm articleForm,BindingResult result, Model model) {
		
		System.out.println();
		if(result.hasErrors()) {
			return index(model);
		}

		Article article = new Article();
		BeanUtils.copyProperties(articleForm, article);
		articleRepository.insert(article);

		return "redirect:/article/index";
	}

	/**
	 * コメントを投稿する.
	 * 
	 * @param commentForm コメントのフォーム
	 * @param model リクエストスコープ
	 * @param result 入力値チェック用
	 * @return 記事一覧画面
	 */
	@RequestMapping("/insertComment")
	public String insertComment(@Validated CommentForm commentForm, BindingResult result,Model model) {
		
		System.out.println(commentForm);
		if(result.hasErrors()) {
			return index(model);
		}

		Comment comment = new Comment();
		BeanUtils.copyProperties(commentForm, comment);
		comment.setArticleId(commentForm.getIntArticleId());
		commentRepository.insert(comment);

		return "redirect:/article/index";
	}

	/**
	 * 記事を削除する.
	 * 
	 * @param model　リクエストスコープ
	 * @param id　記事ID
	 * @return　記事一覧画面
	 */
	@RequestMapping("/deleteArticle")
	public String deleteArticle(Model model, int id) {

		commentRepository.deleteByArticleId(id);
		articleRepository.deleteById(id);

		return "redirect:/article/index";
	}

	/**
	 * コメントを削除する.
	 * 
	 * @param model リクエストスコープ
	 * @param id　記事ID
	 * @return　記事一覧画面
	 */
	@RequestMapping("/deleteComment")
	public String deleteComment(Model model, int id) {
		
		commentRepository.deleteByArticleId(id);
		return "redirect:/article/index";

	}

}
