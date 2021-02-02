package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

public class BoardDao {

	Connection con;
	PreparedStatement ps;
	ResultSet rs;
	
	
	//데이터 베이스 커넥션풀을 사용하도록 설정하는 메소드
	public void getCon() {
		try {
			Context initctx = new InitialContext();
			Context envctx = (Context) initctx.lookup("java:comp/env");
			DataSource ds = (DataSource) envctx.lookup("jdbc/pool");
			con = ds.getConnection();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//하나의 새로운 게시글이 넘어와서 저장되는 메소드
	public void insertBoard(BoardBean bean) {
		
		getCon();
		
		//bean 클래스에 넘어오지 않았던 데이터들을 초기화 해주어야 합니다.
		int ref=0; // 글 그룹을 의미 = 쿼리를 실행시켜서 가장 큰 ref값을 가져온 후 +1를 더해주면 됨
		int re_step=1; // 새글이게에 = 부모글 여기에
		int re_level=1;
		
		try {
			//가장 큰 ref값을 읽어오는 쿼리 준비
			String refsql = "select max(ref) from board2";
			//쿼리 실행 객체
			ps = con.prepareStatement(refsql);
			//쿼리 실행후 결과를 리턴
			rs = ps.executeQuery();
			if(rs.next()) {
				ref = rs.getInt(1)+1; //최대값에 +1을 더해서 글 그룹을 설정
			}
			//실제로 게시글 전체값을 테이블에 저장
			String sql = "insert into board2 values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			ps = con.prepareStatement(sql);
			
			//?값을 맵핑
			ps.setString(1, bean.getWriter());
			ps.setString(2, bean.getEmail());
			ps.setString(3, bean.getSubject());
			ps.setString(4, bean.getPassword());
			ps.setInt(5, ref);
			ps.setInt(6, re_step);
			ps.setInt(7, re_level);
			ps.setString(8, bean.getContent());
			
			//쿼리 실행
			ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	//모든 리스트를 리턴해주는 메소드 작성 
	public Vector<BoardBean> getAllBoard(int start, int end){
		//리턴할 객체 선언
		Vector<BoardBean> v  =new Vector<BoardBean>();
		
		getCon();
		
		try {
			String sql = "select * from (select A.* ,Rownum Rnum from (select *from board2 order by ref desc ,re_step asc)A) "
						+"where Rnum >= ? and Rnum <= ?";
			
			ps = con.prepareStatement(sql);
			ps.setInt(1, start);
			ps.setInt(2, end);
			
			rs = ps.executeQuery();
			
			while (rs.next()) {
				//데이터를 패키징
				BoardBean bean = new BoardBean();
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));

				v.add(bean);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return v;
 	}
	
	//BoardInfo 하나의 게시글을 리턴하는 메소드
	public BoardBean getOneBoard(int num){
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			//조회수 증가 쿼리
			String readsql = "update board2 set readcount = readcount+1 where num=?";
			ps = con.prepareStatement(readsql);
			ps.setInt(1, num);
			ps.executeUpdate();
			
			String sql = "select * from board2 where num=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	
	//답변글이 저장되는 메소드
	public void reWriteBoard(BoardBean bean) {
		//부모글 그룹과 글레벨 글스텝을 읽어드림
		int ref = bean.getRef();
		int re_step = bean.getRe_step();
		int re_level = bean.getRe_level();
		
		
		getCon();
		
		try {
			////////////핵심코드////////////
			//부모 글보다 큰  re_level의 값을 전부 1씩 증가시켜줌
			String levelspl = "update board2 set re_level=re_level+1 where ref=? and re_level > ?";
			
			ps = con.prepareStatement(levelspl);
			ps.setInt(1, ref);
			ps.setInt(2, re_level);
			ps.executeUpdate();
			
			String sql = "insert into board2 values(board_seq.nextval,?,?,?,?,sysdate,?,?,?,0,?)";
			ps=con.prepareStatement(sql);
			
			ps.setString(1, bean.getWriter());
			ps.setString(2, bean.getEmail());
			ps.setString(3, bean.getSubject());
			ps.setString(4, bean.getPassword());
			ps.setInt(5, ref); //부모의 ref값을 넣어줌
			ps.setInt(6, re_step+1); // 답글이기에 부모 글 re_step에 1을 더해줌
			ps.setInt(7, re_level+1);
			ps.setString(8, bean.getContent());
			
			//쿼리 실행
			ps.executeUpdate();
			 
			con.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//BoardUpdated와 delete시 하나의 게시글을 리턴
	public BoardBean getOneUpateBoard(int num){
		BoardBean bean = new BoardBean();
		getCon();
		
		try {
			String sql = "select * from board2 where num=?";
			ps = con.prepareStatement(sql);
			ps.setInt(1, num);
			
			rs = ps.executeQuery();
			if(rs.next()) {
				bean.setNum(rs.getInt(1));
				bean.setWriter(rs.getString(2));
				bean.setEmail(rs.getString(3));
				bean.setSubject(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setReg_date(rs.getDate(6).toString());
				bean.setRef(rs.getInt(7));
				bean.setRe_step(rs.getInt(8));
				bean.setRe_level(rs.getInt(9));
				bean.setReadcount(rs.getInt(10));
				bean.setContent(rs.getString(11));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return bean;
	}
	
	
	//Update와 Delete시 필요한 패스워드 값을 리턴해 주는 메소드
	public String getPass(int num) {
		String pass = "";
		getCon();
		
		try {
			String sql = "select password from board2 where num =?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, num);
		rs = ps.executeQuery();
		
		if(rs.next()) {
			pass = rs.getString(1);//패스워드값을 저장
		}
		
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return pass;
	}
	
	
	//하나의 게시글을 수정하는 메소드
	public void updateBoard(BoardBean bean) {
		
		getCon();
		try {
			String sql = "update board2 set subject=?, content=? where num=?";
		ps = con.prepareStatement(sql);
		ps.setString(1, bean.getSubject());
		ps.setString(2, bean.getContent());
		ps.setInt(3, bean.getNum());
		
		ps.executeUpdate();
		
		con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//하나의 게시글을 삭제하는 메소드
	public void deleteBoard(int num) {
		getCon();
		
		try {
			String sql = "delete from board2 where num=?";
			ps =con.prepareStatement(sql);
			ps.setInt(1, num);
			ps.executeUpdate();
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	//전체 글의 갯수를 리턴하는 메소드
	public int getAllcount() {
		getCon();
		int count =0;
		
		try {
			String sql = "select count(*) from board2";
			ps = con.prepareStatement(sql);
			rs = ps.executeQuery();
			
			if(rs.next()) {
				count = rs.getInt(1);
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
