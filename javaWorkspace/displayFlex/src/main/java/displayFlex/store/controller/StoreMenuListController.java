package displayFlex.store.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import displayFlex.store.service.StoreService;
import displayFlex.store.vo.StoreVo;

@WebServlet("/store/menu")
public class StoreMenuListController extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// 메뉴 목록조회해서 JSON 형태로 문자열 내보내기
		try {
			//data
			String cate = req.getParameter("category");  //카테고리에 해당하는 문자열 가져오기
			
			//service
			StoreService ss = new StoreService();
			List <StoreVo> voList = ss.storeMenuList(cate);
			
			//result (StoreList를 JSON 문자열로 바꿔서 내보내기)
			Gson gson = new Gson();
			String str = gson.toJson(voList); //객체를 JSON형식문자열 변환 함수 호출
			req.setCharacterEncoding("UTF-8"); //한글처리
			PrintWriter out = resp.getWriter(); // 문자열 내보내기를 위한 통로 준비 (JSP 필요없음)
			out.write(str);  // JSON 으로 변환된 문자열을 내보냄
			
		}catch(Exception e) {
			System.out.println("[ERROR-S002] 스토어 메뉴목록 불러오기 실패...");
			e.printStackTrace();
			req.setAttribute("errorMsg", "스토어 메뉴목록 불러오기 실패...");
			req.getRequestDispatcher("/WEB-INF/views/common.error.jsp").forward(req, resp);
		}
	
	}//doGet
	
}//class
