package com.kh.ex02.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PagingDto {
	private int page = 1;	  // 현재 페이지
	private int startRow;	  // 시작 행번호(rnum betwean A)
	private int endRow;		  // 끝 행번호(                 and B)
	private int startPage;    // 1 2 3, ..., 10 : 1
	private int endPage;	  // 1 2 3, ..., 10 : 10
	private int perPage = 10; // 한페지당 게시글 수 (N줄씩 보기)
	private int totalCount;   // 전체 데이터 갯수
	private int totalPage;    // 전체 페이지
	private final int PAGE_BLOCK_COUNT = 10;
	private String searchType;
	private String keyword;
	
	public PagingDto(int page, int perPage, int count, 
			String searchType, String keyword) {
		this.page = page;
		this.perPage = perPage;
		this.totalCount = count;
		this.searchType = searchType;
		this.keyword = keyword;
		calc();
	}
	
	public void calc() {
		this.endRow = this.page * this.perPage;
		this.startRow = this.endRow - (this.perPage - 1);
		// page		startPage		endPage
		// 1		1				10
		// 2		1				10
		// 11		11				20
		// 12 		11				20
		this.endPage = (int)(Math.ceil(page / (double)PAGE_BLOCK_COUNT)) * PAGE_BLOCK_COUNT;
		this.startPage = this.endPage - (PAGE_BLOCK_COUNT - 1);
		
		// count	totalPage
		// 500		50
		// 501		51
		
		this.totalPage = (int)Math.ceil(this.totalCount / (double)perPage);
		
		if (this.endPage > this.totalPage) {
			this.endPage = this.totalPage;
		}
	}

	
}
