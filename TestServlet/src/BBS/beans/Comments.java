package BBS.beans;

import java.util.Date;

public class Comments {

	private int _id;
	private String _text;
	private int _userId;
	private int _postId;
	private Date _createdDate;
	private Date _updatedDate;

	//Comments.idのセッター・ゲッター
	public void setId(int id){
		_id = id;
	}
	public int getId(){
		return _id;
	}

	//Comments.textのセッター・ゲッター
	public void setText(String text){
		_text = text;
	}
	public String getText(){
		return _text;
	}

	//Comments.user_idのセッター・ゲッター
	public void setUserId(int userId){
		_userId = userId;
	}
	public int getUserId(){
		return _userId;
	}

	//Comments.post_idのセッター・ゲッター
	public void setPostId(int postId){
		_postId = postId;
	}
	public int getPostId(){
		return _postId;
	}

	//Comments.created_dateのセッター・ゲッター
	public void setCreatedDate(Date createdDate){
		_createdDate = createdDate;
	}
	public Date getCreatedDate(){
		return _createdDate;
	}

	//Comments.updated_dateのセッター・ゲッター
	public void setUpdatedDate(Date updatedDate){
		_updatedDate = updatedDate;
	}
	public Date getUpdatedDate(){
		return _updatedDate;
	}

}
