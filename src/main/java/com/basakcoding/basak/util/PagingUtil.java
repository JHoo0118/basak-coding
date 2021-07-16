package com.basakcoding.basak.util;

import java.util.Map;

public class PagingUtil {

	public static String pagingBootStrapStyle(int totalCourseCount, int pageSize, int blockPage, int nowPage,
			String page, Map map) {
		String pagingStr = "<nav><ul class=\"pagination\" >";
		String searchQuery = "";
		//카테고리 검색
		if(map.get("categoryColumn") != null) {
			searchQuery += "&categoryColumn="+map.get("categoryColumn");
		}
		//검색 유무
		if(map.get("searchText") != null) {
			searchQuery += "&searchText="+map.get("searchText")+"&searchColumn="+map.get("searchColumn");
		}
		// 1.전체 페이지 구하기
		int totalPage = (int) (Math.ceil(((double) totalCourseCount / pageSize)));

		int intTemp = ((nowPage - 1) / blockPage) * blockPage + 1;

		// 처음 및 이전을 위한 로직
		if (intTemp != 1) {
			pagingStr += "<li>\r\n" + "<a href='" + page + "?nowPage=1"+searchQuery+"'>\r\n" + "<span >&laquo;</span>\r\n" + "</a>\r\n"
					+ "</li>\r\n" + "<li >\r\n" + "<a href='" + page + "?nowPage=" + (intTemp - blockPage) + searchQuery+"' >\r\n"
					+ "<span >&lsaquo;</span>\r\n" + "</a>\r\n" + "</li>";

		}

		// 페이지 표시 제어를 위한 변수
		int blockCount = 1;

		// 페이지를 뿌려주는 로직
		// 블락 페이지 수만큼 혹은 마지막 페이지가 될때까지 페이지를 표시한다1
		while (blockCount <= blockPage && intTemp <= totalPage) { // 페이지 오버 를 체크
			// 현재 페이지를 의미함
			if (intTemp == nowPage) {
				pagingStr += "<li><a href='#'><span style='Color:red'>" + intTemp + "</span></a></li>";
			} else
				pagingStr += "<li><a href='" + page + "?nowPage=" + intTemp + searchQuery+"'>" + intTemp + "</a></li>";

			intTemp = intTemp + 1;
			blockCount = blockCount + 1;

		}

		// 다음 및 마지막을 위한 로직

		if (intTemp <= totalPage) {
			pagingStr += "<li>\r\n" + "<a href='" + page + "?nowPage=" + intTemp + searchQuery+"'>\r\n"
					+ "<span >&rsaquo;</span>\r\n" + "</a>\r\n" + "</li>\r\n" + "<li>\r\n" + "<a href='" + page
					+ "?nowPage=" + totalPage + searchQuery+"' >\r\n" + "<span >&raquo;</span>\r\n" + "</a>\r\n" + "</li>";

		}
		pagingStr += "</ul></nav>";
		return pagingStr;
	}

}
