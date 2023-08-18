package com.himedia.springboot;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardDAO {
	ArrayList<BoardDTO> getList(int start, int psize);
	
	BoardDTO view(int seqno);
	
	void hitUp(int seqno);
	
	void delPost(int seqno);
	
	void insPost(String title, String content, String writer, String created, String updated);
	
	void udPost(int seqno, String title, String content, String updated);
	
	int getTotal();
	
//	int login(String userid, String passcode);
	
	int signup(String userid, String passcode);
	
	BoardDTO login(String userid, String passcode);
}
