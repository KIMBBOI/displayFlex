package displayFlex.ticketing.payment.service;

import java.sql.Connection;
import java.util.List;

import displayFlex.ticketing.payment.dao.PaymentDao;
import displayFlex.ticketing.payment.vo.PaymentVo;
import displayFlex.ticketing.payment.vo.SelectCouponVo;
import displayFlex.ticketing.payment.vo.UserGradeVo;
import test.JDBCTemplate;

public class PaymentService {

	public List<SelectCouponVo> getCouponList(String memberNo) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		PaymentDao dao = new PaymentDao();
		List<SelectCouponVo> couponList = dao.getCouponList(conn, memberNo);
		
		JDBCTemplate.close(conn);
		
		return couponList;
	}

	public SelectCouponVo getSelectCouponInfo(String selectedRetainedNo) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		PaymentDao dao = new PaymentDao();
		
		SelectCouponVo vo = dao.getSelectCouponInfo(selectedRetainedNo, conn);
		
		JDBCTemplate.close(conn);
		
		return vo;
	}

	public UserGradeVo getUserGrade(String memberNo) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		PaymentDao dao = new PaymentDao();
		
		UserGradeVo vo = dao.getUserGrade(memberNo, conn);
		
		JDBCTemplate.close(conn);
		
		return vo;
	}

	public int setMoviePayment(PaymentVo paymentVo) throws Exception {
		
		Connection conn = JDBCTemplate.getConnection();
		
		PaymentDao dao = new PaymentDao();
		
		int payResult = dao.setMoviePayment(paymentVo, conn);
		
		if(payResult == 1) {
			int couponResult = dao.setCouponStatus(paymentVo.getRetainedNo(), conn);
			JDBCTemplate.commit(conn);
			
			if(couponResult == 1) {
				String foreignKey = dao.getForeignKey(paymentVo, conn);
				
				int ticketResult = dao.setTicket(foreignKey, paymentVo, conn);
				
				JDBCTemplate.commit(conn);
			} else {
				JDBCTemplate.rollback(conn);
			} 
		} else {
			JDBCTemplate.rollback(conn);
		}
		
		JDBCTemplate.close(conn);
		
		return 1;
	}

}
