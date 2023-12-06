package displayFlex.movie.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import displayFlex.movie.dto.MovieDetailDto;
import displayFlex.movie.dto.MovieListDto;
import displayFlex.movie.vo.GenreCategoryVo;
import displayFlex.movie.vo.ScreenGradeVo;
import displayFlex.movie.vo.StillImageVo;
import displayFlex.util.page.vo.PageVo;
import test.JDBCTemplate;

public class MovieDao {

	private String query;
	
	/**
	 * 모든 장르 유형 가져오기 
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<GenreCategoryVo> getAllGenreCategory(Connection con) throws SQLException {
		query = "SELECT * FROM GENRE_CATEGORY";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		List<GenreCategoryVo> genreList = new ArrayList<GenreCategoryVo>();
		
		while(rs.next()) {
			String genreCateNo = rs.getString("GENRE_CATE_NO");
			String cateName = rs.getString("CATE_NAME");
			
			genreList.add(new GenreCategoryVo(genreCateNo, cateName));
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return genreList;
	}

	/**
	 * 관람 등급 정보 가져오기
	 * @param con
	 * @return
	 * @throws SQLException
	 */
	public List<ScreenGradeVo> getAllScreenGrade(Connection con) throws SQLException {
		query = "SELECT * FROM SCREEN_GRADE";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		List<ScreenGradeVo> screenGradeList = new ArrayList<ScreenGradeVo>();
		
		while (rs.next()){
			String screenGradeNo = rs.getString("SCREEN_GRADE_NO");
			String name = rs.getString("NAME");
			
			screenGradeList.add(new ScreenGradeVo(screenGradeNo, name));
		}
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return screenGradeList;
	}

	public int getAllMovieCount(Connection con) throws SQLException {
		query = "SELECT COUNT(*) FROM MOVIE";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		ResultSet rs = pstmt.executeQuery();
		int result = 0;
		
		if(rs.next()) {
			result = rs.getInt(1);
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return result;
	}

	/**
	 * 영화 목록 가져오기
	 * @param page
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<MovieListDto> getAllMovieList(PageVo page, Connection con) throws SQLException {
		query = "SELECT A.* , SG.NAME FROM ( SELECT ROW_NUMBER() OVER(ORDER BY M.WRITE_DATE DESC) RNUM , M.MOVIE_NO , M.MOVIE_NAME , M.SCREEN_GRADE_NO , TO_CHAR(M.RELEASE_DATE, 'YYYY.MM.DD') RELEASE_DATE , M.MOVIE_IMAGE , M.RUNNING_TIME , M.RATE , M.GENRE FROM MOVIE M ) A INNER JOIN SCREEN_GRADE SG ON A.SCREEN_GRADE_NO = SG.SCREEN_GRADE_NO WHERE A.RNUM BETWEEN ? AND ?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setInt(1, page.getStartRow());
		pstmt.setInt(2, page.getLastRow());
		ResultSet rs = pstmt.executeQuery();
		List<MovieListDto> movieList = new ArrayList<MovieListDto>();
		
		while(rs.next()) {
			String movieNo = rs.getString("MOVIE_NO");
			String movieName = rs.getString("MOVIE_NAME");
			String name = rs.getString("NAME");
			String movieImage = rs.getString("MOVIE_IMAGE");
			String releaseDate = rs.getString("RELEASE_DATE");
			String runningTime = rs.getString("RUNNING_TIME");
			String rate = rs.getString("RATE");
			String genre = rs.getString("GENRE");
			
			movieList.add(new MovieListDto(movieNo, movieName, name, movieImage, releaseDate, runningTime, rate, genre));
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return movieList;
	}

	/**
	 * 영화 상세 정보 가져오기
	 * @param movieNo
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public MovieDetailDto getMovieInfoByNo(String movieNo, Connection con) throws SQLException {
		query = "SELECT M.MOVIE_NO, M.MOVIE_NAME, SG.NAME, M.ACTORS, M.STORY, M.RATE, M.MAIN_DIRECTOR, M.MOVIE_IMAGE, M.RUNNING_TIME,TO_CHAR(M.RELEASE_DATE, 'YYYY-MM-DD') RELEASE_DATE, M.GENRE, M.NATION FROM MOVIE M INNER JOIN SCREEN_GRADE SG ON M.SCREEN_GRADE_NO = SG.SCREEN_GRADE_NO WHERE M.MOVIE_NO = ?";
		
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, movieNo);
		ResultSet rs = pstmt.executeQuery();
		
		MovieDetailDto findMovie = null;
		if(rs.next()) {
			String findMovieNo = rs.getString("MOVIE_NO");			
			String movieName =  rs.getString("MOVIE_NAME");	
			String gradeName = rs.getString("NAME");
			String actors = rs.getString("ACTORS");
			String story = rs.getString("STORY");
			String rate = rs.getString("RATE");
			String mainDirector = rs.getString("MAIN_DIRECTOR");
			String movieImage = rs.getString("MOVIE_IMAGE");	
			String  runningTime = rs.getString("RUNNING_TIME");
			String releaseDate = rs.getString("RELEASE_DATE");
			String genre = rs.getString("GENRE");
			String nation = rs.getString("NATION");
			findMovie = new MovieDetailDto(findMovieNo, movieName, gradeName, actors, story, rate, mainDirector, movieImage, runningTime, releaseDate, genre, nation);
		}
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return findMovie;
	}

	
	public List<StillImageVo> getStillImageByMovieNo(String movieNo, Connection con) throws SQLException {
		query= "SELECT * FROM STILL_IMAGE_FILE WHERE MOVIE_NO  = ?";
		PreparedStatement pstmt = con.prepareStatement(query);
		pstmt.setString(1, movieNo);
		ResultSet rs = pstmt.executeQuery();
		List<StillImageVo> imageList= new ArrayList<StillImageVo>();
		
		while(rs.next()) {
			String stillNo = rs.getString("STILL_NO");
			String findMovieNo = rs.getString("MOVIE_NO");
			String filePath = rs.getString("FILE_NAME");
			
			imageList.add(new StillImageVo(stillNo, findMovieNo, filePath));
		}
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return imageList;
	}

	/**
	 * 조건에 맞는 영화 개수 가져오기
	 * @param genres
	 * @param grade
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public int getAllMovieCountByCondition(String[] genres, String grade, Connection con) throws SQLException {
		StringBuilder dynamicQuery = new StringBuilder("SELECT COUNT(DISTINCT M.MOVIE_NO) FROM MOVIE M ");
		int index = 1;
		
		
		//조건에 따라 쿼리문 동적 생성
		if(genres != null) {
			dynamicQuery.append("INNER JOIN MOVIE_CATE MC ON M.MOVIE_NO = MC.MOVIE_NO WHERE ");
		} else if(grade != null) {
			dynamicQuery.append("WHERE ");			
		}
		
		if(genres != null) {
			dynamicQuery.append("MC.GENRE_CATE_NO IN (");

	        for (int i = 0; i < genres.length; i++) {
	            dynamicQuery.append("?");
	            if (i < genres.length - 1) {
	                dynamicQuery.append(", ");
	            } else {
	            	dynamicQuery.append(") ");
	            }
	        }
		}
		
		if(genres != null && grade != null) {
			dynamicQuery.append("AND ");
		}
		
		if(grade != null) {
			dynamicQuery.append("M.SCREEN_GRADE_NO = ? ");
		}
		
		PreparedStatement pstmt = con.prepareStatement(dynamicQuery.toString());
		System.out.println("dynamicQuery = " + dynamicQuery.toString());
		
		if(genres != null) {
			for(int count = 0; count <genres.length; count++) {
				pstmt.setString(index, genres[count]);
				index++;
			}			
		}
		
		if(grade != null) {
			pstmt.setString(index, grade);
			index++;
		}
		
		int count = 0;
		ResultSet rs = pstmt.executeQuery();

		while(rs.next()) {
			count = rs.getInt(1);
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return count;
	}

	/**
	 * 조건에 맞는 영화 리스트 가져오기
	 * @param genres
	 * @param grade
	 * @param page
	 * @param con
	 * @return
	 * @throws SQLException 
	 */
	public List<MovieListDto> findMoiveListByCondition(String[] genres, String grade, PageVo page, Connection con) throws SQLException {
		StringBuilder dynamicQuery = new StringBuilder("SELECT A.*, SG.NAME FROM ( SELECT ROW_NUMBER() OVER(ORDER BY M.WRITE_DATE DESC) RNUM ,  M.MOVIE_NO , M.MOVIE_NAME , M.SCREEN_GRADE_NO , TO_CHAR(M.RELEASE_DATE, 'YYYY.MM.DD') RELEASE_DATE , M.MOVIE_IMAGE , M.RUNNING_TIME , M.RATE , M.GENRE FROM MOVIE M WHERE M.MOVIE_NO IN (SELECT DISTINCT O.MOVIE_NO FROM MOVIE O ");
		int index = 1;
		
		//조건에 따라 쿼리문 동적 생성
		if(genres != null) {
			dynamicQuery.append("INNER JOIN MOVIE_CATE MC ON O.MOVIE_NO = MC.MOVIE_NO WHERE ");
		} else if(grade != null) {
			dynamicQuery.append("WHERE ");			
		}
		
		if(genres != null) {
			dynamicQuery.append("MC.GENRE_CATE_NO IN (");

	        for (int i = 0; i < genres.length; i++) {
	            dynamicQuery.append("?");
	            if (i < genres.length - 1) {
	                dynamicQuery.append(", ");
	            } else {
	            	dynamicQuery.append(") ");
	            }
	        }
		}
		if(genres != null && grade != null) {
			dynamicQuery.append("AND ");
		}
		
		if(grade != null) {
			dynamicQuery.append("M.SCREEN_GRADE_NO = ? ");
		}
		
		dynamicQuery.append(")) A INNER JOIN SCREEN_GRADE SG ON A.SCREEN_GRADE_NO = SG.SCREEN_GRADE_NO WHERE A.RNUM BETWEEN ? AND ?");
		
		PreparedStatement pstmt = con.prepareStatement(dynamicQuery.toString());
		
		if(genres != null) {
			for(int count = 0; count <genres.length; count++) {
				pstmt.setString(index, genres[count]);
				index++;
			}			
		}
		
		if(grade != null) {
			pstmt.setString(index, grade);
			index++;
		}
		
		pstmt.setInt(index, page.getStartRow());
		index++;
		pstmt.setInt(index, page.getLastRow());
		
		ResultSet rs = pstmt.executeQuery();
		List<MovieListDto> movieList = new ArrayList<MovieListDto>();
		while(rs.next()) {
			String movieNo = rs.getString("MOVIE_NO");
			String movieName = rs.getString("MOVIE_NAME");
			String name = rs.getString("NAME");
			String movieImage = rs.getString("MOVIE_IMAGE");
			String releaseDate = rs.getString("RELEASE_DATE");
			String runningTime = rs.getString("RUNNING_TIME");
			String rate = rs.getString("RATE");
			String genre = rs.getString("GENRE");
			
			movieList.add(new MovieListDto(movieNo, movieName, name, movieImage, releaseDate, runningTime, rate, genre));
		}
		
		JDBCTemplate.close(rs);
		JDBCTemplate.close(pstmt);
		return movieList;
	}

	
	

}