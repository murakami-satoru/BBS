package BBS.beans;

public class Departments {

	private int _id;
	private String _name;

	//Departments.idのセッター・ゲッター
	public void setId(int id){
		_id = id;
	}
	public int getId(){
		return _id;
	}

	//Departments.nameのセッター・ゲッター
	public void setName(String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
}
