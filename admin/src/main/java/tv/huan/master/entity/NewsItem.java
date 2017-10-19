package tv.huan.master.entity;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import tv.huan.master.common.entity.BaseEntity;

@Entity
@Table(name="newsitem")
public class NewsItem extends BaseEntity {
	private String title;
	private String author;
	private String digest;
	private String show_cover;
	private String cover_url;
	private String content_url;
	private String content;
	@ManyToOne()
	@JoinColumn(name = "rid",referencedColumnName="id",nullable=true)
	private ReplyInfo replyinfo;
	public String getTitle() {
		return title;
	}

	public ReplyInfo getReplyinfo() {
		return replyinfo;
	}

	public void setReplyinfo(ReplyInfo replyinfo) {
		this.replyinfo = replyinfo;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getDigest() {
		return digest;
	}
	public void setDigest(String digest) {
		this.digest = digest;
	}
	public String getShow_cover() {
		return show_cover;
	}
	public void setShow_cover(String show_cover) {
		this.show_cover = show_cover;
	}
	public String getCover_url() {
		return cover_url;
	}
	public void setCover_url(String cover_url) {
		this.cover_url = cover_url;
	}
	public String getContent_url() {
		return content_url;
	}
	public void setContent_url(String content_url) {
		this.content_url = content_url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
