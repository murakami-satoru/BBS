package BBS.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import BBS.beans.Users;

public class UsersDao {

	public Users getUsers(Connection connection, String loginId, String password){
		String sql = "select * from users where login_id = ? and password = ?";
		try(PreparedStatement statement = connection.prepareStatement(sql)){
			statement.setString(1, loginId);
			statement.setString(2, password);

			ResultSet results = statement.executeQuery();
			List<Users> user = toUsersList(results);
			if(user.isEmpty()){
				return null;
			}else if(2 <= user.size()){
				throw new IllegalStateException("2 <= user.size()");
			}else{
				return user.get(0);
			}
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}


	public void insert(Connection connection,Users users){
		StringBuilder sql = new StringBuilder();
		sql.append("insert into users ("
				  + "login_id ,"
				  + "password ,"
				  + "name ,"
				  + "branch_id ,"
				  + "department_id"
				  + " ) values ( "
				  + "? ,"	//login_id
				  + "? ,"	//password
				  + "? ,"	//name
				  + "? ,"	//branch_id
				  + "? )"	//department_id
				);

		try(PreparedStatement statement = connection.prepareStatement(sql.toString())){
			statement.setString(1, users.getLoginId());
			statement.setString(2, users.getPassword());
			statement.setString(3, users.getName());
			statement.setInt(4, users.getBranchId());
			statement.setInt(5, users.getDepartmentId());

			statement.executeUpdate();
		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
	}

	public List<Users> selectAll(Connection connection){
		List<Users> users = new ArrayList<Users>();
		String sql = "select * from users order by id";

		try(PreparedStatement statement = connection.prepareStatement(sql);
			ResultSet result = statement.executeQuery();){

			while(result.next()){
				Users user = new Users();
				user.setId(result.getInt("id"));
				user.setLoginId(result.getString("login_id"));
				user.setPassword(result.getString("password"));
				user.setName(result.getString("name"));
				user.setBranchId(result.getInt("branch_id"));
				user.setDepartmentId(result.getInt("department_id"));
				user.setIsLocked(result.getInt("is_locked"));

				users.add(user);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return users;
	}

	public List<Users> toUsersList(ResultSet results){
		List<Users> users = new ArrayList<Users>();
		try{
			while(results.next()){
				Users user = new Users();
				user.setId(results.getInt("id"));
				user.setLoginId(results.getString("login_id"));
				user.setPassword(results.getString("password"));
				user.setName(results.getString("name"));
				user.setBranchId(results.getInt("branch_id"));
				user.setDepartmentId(results.getInt("department_id"));
				user.setIsLocked(results.getInt("is_locked"));

				users.add(user);
			}

		} catch (SQLException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}

		return users;
	}

}
