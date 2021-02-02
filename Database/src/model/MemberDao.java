package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

//오라클 데이터 베이스에 연결하고 select, insert, update, delete작업을 실행해주는 클래스.
public class MemberDao {
	
	// 오라클에 접속하는 소스를 작성
	String id="system";
	String pass="oracle";
	String url="jdbc:oracle:thin:@localhost:1521:XE"; // 접속 url
	
	Connection con; // 데이터베이스에 접근할 수 있도록 설정
	PreparedStatement ps; // 데이터 베이스에서 쿼리를 실행시켜주는 객체
	ResultSet rs; // 데이터베이스의 테이블의 결과를 리턴받아 자바에 저장해주는 객체
	
	//데이터 베이스 접근 할 수 있도록 조와주는 메소드
	public void getCon() {
		
		//커넥션풀을 이용하여 데이터 베이스에 접근
		try {
			Context initctx = new InitialContext();
			
			//톰캣 서버에 정보를 담아놓은 곳으로 이동
			Context envctx =(Context) initctx.lookup("java:comp/env");
			
			//데이터 소스 객체를 선언
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			
			//데이터 소스를 기준으로 커넥션을 연결해주시오.
			con = ds.getConnection();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		/*
		 * try{ //1. 해당 데이터 베이스를 사용한다고 선언(클래스를 등록=오라클용을 사용)
		 * Class.forName("oracle.jdbc.driver.OracleDriver");
		 * 
		 * //2. 해당 데이터 베이스에 접속 con = DriverManager.getConnection(url,id,pass);
		 * }catch(Exception e){ e.printStackTrace(); }
		 */
	}
	
	//데이터 베이스에 한 사람의 데이터 회원 정보를 저장해주는 메소드
	public void insertMember(MemberBean mbean) {
		
		try{
			getCon();
			//3. 접속 후 쿼리를 준비
			String sql ="insert into Member values(?,?,?,?,?,?,?,?)";
			
			// 쿼리를 사용하도록 설정 
			PreparedStatement pstmt = con.prepareStatement(sql); //jsp에서 쿼리를 사용하도록 설정
			
			//?에 맞게 데이터를 맵핑
			pstmt.setString(1, mbean.getId());
			pstmt.setString(2, mbean.getPass1());
			pstmt.setString(3, mbean.getEmail());
			pstmt.setString(4, mbean.getTel());
			pstmt.setString(5, mbean.getHobby());
			pstmt.setString(6, mbean.getJob());
			pstmt.setString(7, mbean.getAge());
			pstmt.setString(8, mbean.getInfo());
			
			//4. 오라클에서 쿼리를 실행
			pstmt.executeUpdate();//insert, update, delete 시 사용하는 메소드
			
			//5. 자원반환
			con.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	//모든 회원의 정보를 리턴해주는 메소드 호출
	public Vector<MemberBean> allSelectMember(){
		//가변길이로 데이터를 저장
		Vector<MemberBean> v = new Vector<>();
		
		
		//무조건(IO 데이터베이스 스레드 네트워크)는 예외처리를 반드시 해야함.
		try {
			getCon();
			
			//쿼리 준비
			String sql = "select * from member";
		
			//쿼리 실행시키는 객체 선언
			ps = con.prepareStatement(sql);
			
			//결과를 리턴해서 받아줌(오라클 테이블의 검색된 결과를 저장)
			rs = ps.executeQuery();
			
			//반복문을 사용해서 rs에 저장된 데이터를 추출해놓아야 함
			while (rs.next()) {//저장된 데이터 만큼까지 반복문을 돌리겠다는 뜻
				MemberBean bean = new MemberBean(); //칼럼으로 나누어진 데이터를 빈클래스에 저장
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
				//패키징된  memberbean클래스를 벡터에 저장
				v.add(bean);//0번지부터 순서대로 데이터가 저장
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return v;
	}
	
	
	
	
	public MemberBean oneSelectMember(String id) {
		//한사람에 대한 정보만 리턴하기에 빈클래스 객체 생성
		MemberBean bean = new MemberBean();
		
		try {
			getCon();
			//쿼리 준비
			String sql = "select * from member where id=?";
			
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			
			if(rs.next()){//레코드가 있다면
				bean.setId(rs.getString(1));
				bean.setPass1(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setTel(rs.getString(4));
				bean.setHobby(rs.getString(5));
				bean.setJob(rs.getString(6));
				bean.setAge(rs.getString(7));
				bean.setInfo(rs.getString(8));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return bean;
	}
	
	//한 회원의 패스워드 값을 리턴하는 메소드
	public String getPass(String id) {
		String pass="";
		
		try {
			getCon();
			String sql="select pass from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				pass = rs.getString(1);
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pass;
	}
	
	//한 회원의 정보를 수정하는 메소드
	public void updateMember(MemberBean bean) {
		
		try {
			getCon();
			String sql = "update member set email=?,tel=? where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, bean.getEmail());
			ps.setString(2, bean.getTel());
			ps.setString(3, bean.getId());
			
			ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//한 회원을 삭제하는 메소드를 작성
	public void deleteMember(String id) {
		try {
			getCon();
			
			String sql = "delete from member where id=?";
			ps = con.prepareStatement(sql);
			ps.setString(1, id);
			
			ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
}
