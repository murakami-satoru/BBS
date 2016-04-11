package BBS.beans;

import java.util.Date;

public class Posts {

	private int _id;
	private String _title;
	private String _text;
	private String _category;
	private int _userID;
	private Date _createdDate;
	private Date _updatedDate;

	//Posts.idのセッター・ゲッター
	public void setId(int id){
		_id = id;
	}
	public int getId(){
		return _id;
	}

	//Posts.titleのセッター・ゲッター
	public void setTitle(String title){
		_title = title;
	}
	public String getTitle(){
		return _title;
	}

	//Posts.textのセッター・ゲッター
	public void setText(String text){
		_text = text;
	}
	public String getText(){
		return _text;
	}
	//Posts.categoryのセッター・ゲッター
	public void setCategory(String category){
		_category = category;
	}
	public String getCategory(){
		return _category;
	}

	//Posts.user_idのセッター・ゲッター
	public void setUserId(int userID){
		_userID = userID;
	}
	public int getUserId(){
		return _userID;
	}

	//Posts.created_dateのセッター・ゲッター
	public void setCreatedDate(Date createdDate){
		_createdDate = createdDate;
	}
	public Date getCreatedDate(){
		return _createdDate;
	}

	//Posts.updated_dateのセッター・ゲッター
	public void setUpdatedDate(Date updatedDate){
		_updatedDate = updatedDate;
	}
	public Date getUpdatedDate(){
		return _updatedDate;
	}

}
