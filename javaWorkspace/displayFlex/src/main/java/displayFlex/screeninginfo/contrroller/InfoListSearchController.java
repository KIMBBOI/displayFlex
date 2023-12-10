package displayFlex.screeninginfo.contrroller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import displayFlex.movie.vo.MovieVo;
import displayFlex.screeninginfo.dto.ScreenInfoDto;
import displayFlex.screeninginfo.service.ScreenInfoService;
import displayFlex.util.page.vo.PageVo;


@WebServlet("/admin/screen-info/search/list")
public class InfoListSearchController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final ScreenInfoService infoService;

    public InfoListSearchController() {
    	infoService =new ScreenInfoService();
    }


	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.service(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			//영화 목록
			List<MovieVo> movieList = infoService.getAllMovie();
			
			//상영관 가져오기
			List<String> theaterList = infoService.getAllTheater();
			
			String title = request.getParameter("title") == "" ? null : request.getParameter("title");
			String theater = request.getParameter("theater") == "" ? null : request.getParameter("theater");
			String screeningDate = request.getParameter("screening-date") == "" ? null : request.getParameter("screening-date");
			String startTime = request.getParameter("startTime") == "" ? null : request.getParameter("startTime");
			String endTime = request.getParameter("endTime") == "" ? null : request.getParameter("endTime");

			ScreenInfoDto screenInfo = new ScreenInfoDto(title, theater, screeningDate, startTime, endTime);
			//영화 리스트 가져오기(페이징 처리 10개씩)
			int pno = request.getParameter("pno") == null ? 1 : Integer.parseInt(request.getParameter("pno"));
			int infoTotalCount = infoService.getTotalCountByCondition(screenInfo);

			PageVo page = setPage(infoTotalCount , pno, 5, 10);
			
			List<ScreenInfoDto> infoList = infoService.getInfoListByCondition(screenInfo, page);
			
			//jsp로 보내기
			request.setAttribute("movieList", movieList);
			request.setAttribute("theater", theaterList);
			request.setAttribute("infoList", infoList);
			request.setAttribute("pageVo", page);
			request.getRequestDispatcher("/WEB-INF/views/screening-info/search.jsp").forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();		
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private PageVo setPage(int listCount, int currentPage, int pageLimit, int boardLimit) {
		return new PageVo(listCount, currentPage, pageLimit, boardLimit);
	}
}
