package tv.huan.master.entity;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@Table(name = "user_answer_like")
@DynamicInsert
@DynamicUpdate
public class Answerlike {
	private AnswerlikeKey answerlikeKey;

	@EmbeddedId  
    @AttributeOverrides({  
        @AttributeOverride(name = "answerid", column = @Column(name="answerid")),  
        @AttributeOverride(name = "userid", column = @Column(name="userid"))  
    })  
	public AnswerlikeKey getAnswerlikeKey() {
		return answerlikeKey;
	}
	public void setAnswerlikeKey(AnswerlikeKey answerlikeKey) {
		this.answerlikeKey = answerlikeKey;
	}
	public Answerlike(AnswerlikeKey answerlikeKey) {
		super();
		this.answerlikeKey = answerlikeKey;
	}
	public Answerlike() {
		super();
	}
	
}
