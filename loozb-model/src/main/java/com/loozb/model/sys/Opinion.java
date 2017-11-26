package com.loozb.model.sys;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.loozb.core.base.BaseModel;
import java.io.Serializable;


/**
 * <p>
 * 权限信息
 * </p>
 *
 * @author LongZhaoBi
 * @since 2017-11-19
 */
@TableName("sys_opinion")
@SuppressWarnings("serial")
public class Opinion extends BaseModel {

    /**
     * 权限名称
     */
	private String title;
    /**
     * 反馈内容
     */
	private String content;
    /**
     * 联系方式
     */
	@TableField("contact_way")
	private String contactWay;

	/**
	 * 回复内容
	 */
	@TableField("reply_content")
	private String replyContent;

	/**
	 * 回复状态
	 */
	@TableField("reply_status")
	private String replyStatus;

	/**
	 * 回复人
	 */
	@TableField("reply_user_id")
	private Long replyUserId;


	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getContactWay() {
		return contactWay;
	}

	public void setContactWay(String contactWay) {
		this.contactWay = contactWay;
	}

	public String getReplyContent() {
		return replyContent;
	}

	public void setReplyContent(String replyContent) {
		this.replyContent = replyContent;
	}

	public String getReplyStatus() {
		return replyStatus;
	}

	public void setReplyStatus(String replyStatus) {
		this.replyStatus = replyStatus;
	}

	public Long getReplyUserId() {
		return replyUserId;
	}

	public void setReplyUserId(Long replyUserId) {
		this.replyUserId = replyUserId;
	}
}