package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jspexp.a13_database.DB;
import vo.Lecture;
import vo.StdLecture;

public class A02_stdLecture {
	private Connection con;
	private PreparedStatement pstmt;
	private ResultSet rs;
	
	
	public List<StdLecture> getListEval(StdLecture get){
		List<StdLecture> elist = new ArrayList<StdLecture>();
		String sql = "SELECT *\r\n"
				+ "FROM stdLecture\r\n"
				+ "WHERE lecNum = ?";
		try {
			con = DB.con();
			
			pstmt = con.prepareStatement(sql);
			pstmt.setInt(1, get.getLecNum());
			rs = pstmt.executeQuery();
			while(rs.next()) {
				StdLecture e = new StdLecture(
							rs.getInt(1),
							rs.getString(2),
							rs.getInt(3),
							rs.getInt(4),
							rs.getInt(5),
							rs.getString(6),
							rs.getString(7)
						);
				elist.add(e);
			}

			
		} catch (SQLException e) {
			System.out.println("DB 에러:"+e.getMessage());
		} catch (Exception e) {
			System.out.println("일반 예외:"+e.getMessage());
		} finally {
			DB.close(rs, pstmt, con);
		}
		return elist;
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
