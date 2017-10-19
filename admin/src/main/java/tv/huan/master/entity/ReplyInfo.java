package tv.huan.master.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.alibaba.fastjson.annotation.JSONField;

import tv.huan.master.common.entity.BaseEntity;
@Entity
@Table(name="replyinfo")
public class ReplyInfo extends BaseEntity{
	public static final String RULE_NAME_DEF="message_default_autoreply_info";
	public static final String RULE_NAME_ADD="add_friend_autoreply_info";
	public static final String RULE_NAME_KEY="keyword_autoreply_info";

	private String rule_name;
	
    @JoinColumn(name="rid",nullable=true)
    @OneToMany(orphanRemoval=true,cascade= CascadeType.ALL)
	@JSONField(name="reply_list_info")
	private List<NewsItem> news_info;
	@JoinColumn(name="rid",nullable=true)
	@OneToMany(orphanRemoval=true,cascade= CascadeType.ALL)
	@JSONField(name="keyword_list_info")
	private List<ReplyMessage> keywords_info;
	private String reply_mode;

	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getReply_mode() {
		return reply_mode;
	}
	public void setReply_mode(String reply_mode) {
		this.reply_mode = reply_mode;
	}
	public String getRule_name() {
		return rule_name;
	}
	public void setRule_name(String rule_name) {
		this.rule_name = rule_name;
	}
	public List<NewsItem> getNews_info() {
		return news_info;
	}
	public void setNews_info(List<NewsItem> news_info) {
		this.news_info = news_info;
	}
	
	public List<ReplyMessage> getKeywords_info() {
		return keywords_info;
	}
	public void setKeywords_info(List<ReplyMessage> keywords_info) {
		this.keywords_info = keywords_info;
	}
	
}
