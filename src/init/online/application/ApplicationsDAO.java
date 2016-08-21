package init.online.application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class ApplicationsDAO {
	
	//获取数据库连接
	public Connection getConnection(){
		String  connectionUrl = "jdbc:mysql://localhost:3306/acct_info??autoReconnect=true&useSSL=false";
		Connection connection = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			connection = DriverManager.getConnection(connectionUrl,"root","123456");
		} catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {			
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	public void closeConnection(Connection connection){
		
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void create(Application application){
		
		String sql = "insert into application (name,price) values(?,?);";
				
		Connection conn = getConnection();
		
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(1, application.getName());
			statement.setDouble(2, application.getPrice());
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn);
		}
	}
	public void remove(int id){
		String sql = "delete from application where id=?";
		Connection conn = getConnection();
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn);
		}	
	}
	
	public void update(int id,  Application entity){
		String sql = "update application set name =? , price=? where id=?;";
		Connection conn = getConnection();
		PreparedStatement statement;
		try {
			statement = conn.prepareStatement(sql);
			statement.setString(1, entity.getName());
			statement.setDouble(2, entity.getPrice());
			statement.setInt(3, id);
			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn);
		}	
	}
	
	public List<Application> selectAll(){
		List<Application> apps = new ArrayList<Application>();
		String sql = "select * from application";
		Connection conn = getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			ResultSet results = statement.executeQuery();
			while(results.next()){
				int id = results.getInt("id");
				String name = results.getString("name");
				Double price = results.getDouble("price");
				Application app = new Application(id,name,price);
				apps.add(app);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn);
		}
		return apps;
	}
	
	public Application selectOne(int id){
		Application app = null;
		String sql = "select * from application where id=?";
		Connection conn = getConnection();
		try {
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet results = statement.executeQuery();
			if(results.next()){
				id = results.getInt("id");
				String name = results.getString("name");
				Double price = results.getDouble("price");
				app = new Application(id,name,price);

			}
		} catch (SQLException e) {
			e.printStackTrace();
		}finally{
			closeConnection(conn);
		}
		return app;
	}

	public static void main(String[] args) {
		ApplicationsDAO dao = new ApplicationsDAO();
		Application app = new Application("Contact List", 3.99);
		dao.create(app);
	}

}
