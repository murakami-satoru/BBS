package BBS.beans;

public class Branches {

	private int _id;
	private String _name;

	//Branches.idのセッター・ゲッター
	public void setId(int id){
		_id = id;
	}
	public int getId(){
		return _id;
	}

	//Branches.nameのセッター・ゲッター
	public void setName(String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}
}
