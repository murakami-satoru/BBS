package BBS.beans;

public class Users {

	private int _id;
	private String _loginId;
	private String _password;
	private String _name;
	private int _branchId;
	private int _departmentId;
	private int _isLocked;

	//User.idのセッター・ゲッター
	public void setId(int id){
		_id = id;
	}
	public int getId(){
		return _id;
	}

	//User.login_idのセッター・ゲッター
	public void setLoginId(String loginId){
		_loginId = loginId;
	}
	public String getLoginId(){
		return _loginId;
	}

	//User.passwordのセッター・ゲッター
	public void setPassword(String password){
		_password = password;
	}
	public String getPassword(){
		return _password;
	}

	//User.nameのセッター・ゲッター
	public void setName(String name){
		_name = name;
	}
	public String getName(){
		return _name;
	}

	//User.branch_idのセッター・ゲッター
	public void setBranchId(int branchId){
		_branchId = branchId;
	}
	public int getBranchId(){
		return _branchId;
	}

	//User.department_idのセッター・ゲッター
	public void setDepartmentId(int departmentId){
		_departmentId = departmentId;
	}
	public int getDepartmentId(){
		return _departmentId;
	}

	//User.is_lockedのセッター・ゲッター
	public void setIsLocked(int isLocked){
		_isLocked = isLocked;
	}
	public int getIsLocked(){
		return _isLocked;
	}
}
