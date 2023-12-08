package displayFlex.screeninginfo.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import displayFlex.movie.vo.MovieVo;
import displayFlex.screeninginfo.vo.ScreeingInfoVo;
import displayFlex.screeninginfo.vo.ScreeningTimeVo;
import test.JDBCTemplate;

public class ScreenInfoDao {

	private String query;
	
	/**
	 * 모든 영화 가져오기 - 검색용
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<MovieVo> getAllMovie(Connection con) throws SQLException {
		query = "SELECT MOVIE_NO, MOVIE_NAME, TO_CHAR(RELEASE_DATE, 'YYYY-MM-DD') RELEASE_DATE FROM MOVIE";
		PreparedStatement pstmt = con.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		List<MovieVo> movieList = new ArrayList<MovieVo>();
		
		while(rs.next()) {
			String movieNo = rs.getString("MOVIE_NO");
			String movietitle = rs.getString("MOVIE_NAME");
			String movieRate = rs.getString("RELEASE_DATE");
			
			movieList.add(new MovieVo(movieNo, movietitle, movieRate));
		}
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return movieList;
	}
	
	/**
	 * 모든 상영관 가져오기
	 * @return
	 * @throws SQLException 
	 */
	public List<String> getAllTheater(Connection con) throws SQLException {
		query = "SELECT THEATER_NO FROM THEATER";
		PreparedStatement pstmt = con.prepareStatement(query);
		
		ResultSet rs = pstmt.executeQuery();
		List<String> theaterList = new ArrayList<String>();
		while(rs.next()) {
			String no = rs.getString("THEATER_NO");
			theaterList.add(no);
		}
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return theaterList;
	}

	/**
	 * 기존의 상영정보 데이터가 있는 지 확인 하기
	 * @param screeingInfo 
	 * @param con 
	 * @return
	 * @throws SQLException 
	 */
	public String getInfoNoByCondition(ScreeingInfoVo screeingInfo, Connection con) throws SQLException {
		query = "SELECT SCREENING_INFO_NO FROM SCREENING_INFO WHERE MOVIE_NO = ? AND = THEATER_NO = ? AND TO_CHAR(START_DATE, 'YYYY-MM-DD') = ?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		
		pstmt.setString(1, screeingInfo.getMovieNo());
		pstmt.setString(2, screeingInfo.getTheaterNo());
		pstmt.setString(3, screeingInfo.getStartDate());
		
		ResultSet rs = pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString(1);
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return null;
	}

	/**
	 * 신규 상영정보 추가 
	 * @param screeingInfoVo
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public int addScreeningInfo(ScreeingInfoVo screeingInfo, Connection con) throws SQLException {
		query = "INSERT INTO SCREENING_INFO(SCREENING_INFO_NO, MOVIE_NO, THEATER_NO, START_DATE) VALUES (SEQ_SCREENING_INFO.NEXTVAL, ?, ? ,TO_TIMESTAMP(?, 'YYYY-MM-DD'))";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, screeingInfo.getMovieNo());
		pstmt.setString(2, screeingInfo.getTheaterNo());
		pstmt.setString(3, screeingInfo.getStartDate());
		
		int result = pstmt.executeUpdate();
		JDBCTemplate.close(pstmt);
		return result;
	}

	/**
	 * 상영 시간 추가
	 * @param screeningTimeVo
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public int addScreeningTime(ScreeningTimeVo screeningTime, Connection con) throws SQLException {
		query = "INSERT INTO SCREENING_TIME(SCREENING_TIME_NO,  SCREENING_INFO_NO, START_TIME, END_TIME) VALUES (SEQ_SCREENING_TIME.NEXTVAL, ?, TO_TIMESTAMP(?, 'HH24:MI:SS'), TO_TIMESTAMP(?, 'HH24:MI:SS'))";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, screeningTime.getScreeningInfoNo());
		pstmt.setString(2, screeningTime.getStartTime());
		pstmt.setString(3, screeningTime.getEndTime());
		
		int result = pstmt.executeUpdate();
		JDBCTemplate.close(pstmt);
		return result;
	}

	/**
	 * 추가한 상영정보
	 * @param screeingInfoVo
	 * @param con
	 */
	public void deleteScreeningInfo(ScreeingInfoVo screeingInfoVo, Connection con) {
		// TODO Auto-generated method stub
		
	}

}
