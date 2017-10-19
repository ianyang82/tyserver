package tv.huan.master.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import tv.huan.master.common.entity.BaseEntity;

/**
 * Created with IntelliJ IDEA.
 * User: warriorr
 * Mail: warriorr@163.com
 * Date: 2014/8/8
 * Time: 16:54
 * To change this template use File | Settings | File Templates
 */
@Entity
@DynamicInsert
@DynamicUpdate
//@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
@Table(name = "weixinmenu")
public class WeixinMenu extends BaseEntity {
	@Column(length=1000)
    private String url;
    private String name;
    private String type;
    private Long parentId;
    @Column(name="showmenu")
    private int isShowMenu = 0;
    private Integer onum;


	public Integer getOnum() {
		return onum;
	}

	public void setOnum(Integer onum) {
		this.onum = onum;
	}

	public String getUrl() {
        return this.url;
    }

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public int getIsShowMenu() {
        return isShowMenu;
    }

    public void setIsShowMenu(int isShowMenu) {
        this.isShowMenu = isShowMenu;
    }

}