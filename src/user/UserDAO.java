package user;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class UserDAO {
	
	private Connection conn;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	public UserDAO(){
		//생성자
		try{//예외처리
			String dbURL = "jdbc:mysql://localhost:3306/BBS";
			String dbID = "root";
			String dbPassword = "5630";
			Class.forName("com.mysql.jdbc.Driver");
			conn = DriverManager.getConnection(dbURL, dbID, dbPassword);
					//localhost 본인 주소 conn=connection의 줄임말
		}catch(Exception e){
			e.printStackTrace();//오류가 무엇인지 출력
		}
	}
    
	public int login(String userID, String userPassword){
		String SQL = "SELECT userPassword FROM USER WHERE userID = ?";
	try{
		pstmt = conn.prepareStatement(SQL);
		pstmt.setString(1, userID);
		rs = pstmt.executeQuery();
		if(rs.next()){
			if(rs.getString(1).equals(userPassword)){
				return 1; // 로그인 성공
			}
			else
				return 0;//비밀번호가 틀림
		}
		return -1; // 아이디가 없음
	}catch(Exception e){
		e.printStackTrace();
	}
	return -2;//데이터 베이스 오류
	
      }
	
	public int join(User user){
		String SQL = "INSERT INTO USER VALUES (?, ?, ?, ?, ?)";
		try{
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, user.getUserID());
			pstmt.setString(2, user.getUserPassword());
			pstmt.setString(3, user.getUserName());
			pstmt.setString(4, user.getUserGender());
			pstmt.setString(5, user.getUserEmail());
		    return pstmt.executeUpdate();
	}catch(Exception e){
		e.printStackTrace();
	}
	return -1;// 데이터베이스 호출
  }
}
