package net.notice.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.action.Action;
import net.action.ActionForward;
import net.board.db.BoardBean;
import net.board.db.BoardDAO;
import net.notice.db.NoticeBean;
import net.notice.db.NoticeDAO;

public class NoticeAddAction implements Action {
	 public ActionForward execute(HttpServletRequest request,HttpServletResponse response) throws Exception{
		NoticeDAO noticedao=new NoticeDAO();
	   	NoticeBean boarddata=new NoticeBean();
	   	ActionForward forward=new ActionForward();
	   	
		String realFolder="";
   		String saveFolder="boardupload";
   		
   		int fileSize=5*1024*1024;
   		
   		realFolder=request.getRealPath(saveFolder);
   		
   		boolean result=false;
   		
   		try{
   			
   			MultipartRequest multi=null;
   			
   			multi=new MultipartRequest(request,
   					realFolder,
   					fileSize,
   					"euc-kr",
   					new DefaultFileRenamePolicy());
   			
   			boarddata.setBOARD_NAME(multi.getParameter("BOARD_NAME"));
   			boarddata.setBOARD_PASS(multi.getParameter("BOARD_PASS"));
	   		boarddata.setBOARD_SUBJECT(multi.getParameter("BOARD_SUBJECT"));
	   		boarddata.setBOARD_CONTENT(multi.getParameter("BOARD_CONTENT"));
	   		boarddata.setBOARD_FILE(
	   				multi.getFilesystemName((String)multi.getFileNames().nextElement()));
	   		
	   		result=boarddao.boardInsert(boarddata);
	   		
	   		if(result==false){
	   			System.out.println("게시판 등록 실패");
	   			boarddao.conClose();
	   			return null;
	   		}
	   		System.out.println("게시판 등록 완료");
	   		
	   		forward.setRedirect(true);
	   		forward.setPath("./BoardList.bo");
	   		boarddao.conClose();
	   		return forward;
	   		
  		}catch(Exception ex){
   			ex.printStackTrace();
   		}
   		boarddao.conClose();
  		return null;
	}  	
}