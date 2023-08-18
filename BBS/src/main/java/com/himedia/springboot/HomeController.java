package com.himedia.springboot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	Date date = new Date();
	SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	String now = formatter.format(date);
	
	@Autowired
	private BoardDAO bdao;
	
	@GetMapping("/")
	public String home(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String userid= (String) session.getAttribute("userid");
		if(userid==null || userid.equals("")) {
			model.addAttribute("login","<a href='/login'>로그인</a>&nbsp;&nbsp;<a href='/signup'>회원가입</a>");
			model.addAttribute("id", "");
			int start,psize;
			String page = req.getParameter("pageno");
			if(page==null || page.equals("")) {
				page="1";
			}
			int pno = Integer.parseInt(page);
			start = (pno-1)*10;
			psize = 10;
			ArrayList<BoardDTO> alBoard = bdao.getList(start, psize);
			
			int cnt=bdao.getTotal();
			int pagecount = (int) Math.ceil(cnt/10.0);

			String pagestr="";
			for(int i=1; i<=pagecount; i++) {
				if(pno==i) {
					pagestr+=i+"&nbsp;";
				} else {
					pagestr+="<a href='/?pageno="+i+"'>"+i+"</a>&nbsp;";
				}
			}
			model.addAttribute("pagestr", pagestr);
			model.addAttribute("blist", alBoard);
		} else {
			model.addAttribute("login","<img src='img/"+session.getAttribute("selficon")+"' width=25%>&nbsp;"+userid+"&nbsp;&nbsp;<button id=btnLogout>로그아웃</button>");
			model.addAttribute("write","<td style='text-align:right;'><a href='/write'><h3>게시물 작성</h3></a></td>");
			model.addAttribute("id", userid);
			int start,psize;
			String page = req.getParameter("pageno");
			if(page==null || page.equals("")) {
				page="1";
			}
			int pno = Integer.parseInt(page);
			start = (pno-1)*10;
			psize = 10;
			ArrayList<BoardDTO> alBoard = bdao.getList(start, psize);
			
			int cnt=bdao.getTotal();
			int pagecount = (int) Math.ceil(cnt/10.0);
			System.out.println("pagecount="+pagecount);
			
			String pagestr="";
			for(int i=1; i<=pagecount; i++) {
				if(pno==i) {
					pagestr+=i+"&nbsp;";
				} else {
					pagestr+="<a href='/?pageno="+i+"'>"+i+"</a>&nbsp;";
				}
			}
			model.addAttribute("pagestr", pagestr);
			model.addAttribute("blist", alBoard);
		}
		return "home";
	}
	
	@GetMapping("/signup")
	public String dosigon() {
		return "signup";
	}
	
	@GetMapping("/login")
	public String login() {
		return "login";
	}
	
	@PostMapping("/doSignup")
	@ResponseBody
	public String doSignon(HttpServletRequest req) {
		String url="";
		try {
			String userid = req.getParameter("userid");
			String passcode = req.getParameter("passcode");
			int n = bdao.signup(userid, passcode);
			if(n>0) {
				url="/login";
			} else {
				url="/signup";
			}
		} catch(Exception e) {
			url="insert 실패";
		}
		return url;
	}
	
	@PostMapping("/doLogin")
	@ResponseBody
	public String doLogin(HttpServletRequest req) {
		try {
			String userid = req.getParameter("userid");
			String passcode = req.getParameter("passcode");
			BoardDTO bdto = bdao.login(userid, passcode);
			if(bdto!=null) {
				HttpSession session = req.getSession();
				session.setAttribute("userid", userid);
				if(bdto.getSelficon()==null) {
					session.setAttribute("selficon", "mini.jpg");
				} else {
					session.setAttribute("selficon", bdto.getSelficon());
				}
				return "/";
			} else {
			return "/login";
			}
		} catch(Exception e) {
			return "login";
		}
	}
	
	 @PostMapping("/logout")
	 @ResponseBody 
	 public String execLogout(HttpServletRequest req) {
			HttpSession session = req.getSession();
			session.setMaxInactiveInterval(1800);
			session.invalidate();	//session 전부 지우기
			return "/";
		}
	
	@GetMapping("/view")
	public String view(HttpServletRequest req, Model model) {
		HttpSession session = req.getSession();
		String userid= (String) session.getAttribute("userid");
		model.addAttribute("id", userid);
		int seqno = Integer.parseInt(req.getParameter("seqno"));
		bdao.hitUp(seqno);
		BoardDTO bdto = bdao.view(seqno);
		model.addAttribute("bPost",bdto);
		return "view";
	}
	
	@GetMapping("/delete")
	public String delete(HttpServletRequest req) {
		int seqno = Integer.parseInt(req.getParameter("seqno"));
		bdao.delPost(seqno);
		return "redirect:/";
	}
	
	@GetMapping("/write")
	public String write(HttpServletRequest req, Model model) {
		return "write";
	}
	@PostMapping("/insert")
	public String insert(HttpServletRequest req) {
		HttpSession session = req.getSession();
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		String userid= (String) session.getAttribute("userid");
		bdao.insPost(title, content, userid, now, now);
		return "redirect:/";
	}
	
	@GetMapping("/update")
	public String update(HttpServletRequest req, Model model) {
		int seqno = Integer.parseInt(req.getParameter("seqno"));
		BoardDTO bdto = bdao.view(seqno);
		model.addAttribute("bPost",bdto);
		return "update";
	}
	@PostMapping("/modify")
	public String modfiy(HttpServletRequest req) {
		int seqno = Integer.parseInt(req.getParameter("seqno"));
		String title = req.getParameter("title");
		String content = req.getParameter("content");
		bdao.udPost(seqno, title, content, now);
		return "redirect:/";
	}
}