package com.innovative.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innovative.dao.FileDao;
import com.innovative.utils.Config;

import java.util.HashMap;
import java.util.Map;
@Service
public class IndexService {

	@Autowired
    CarouselService carouselService;
    @Autowired
    UserService userService;
    @Autowired
    NoticeService noticeService;
    @Autowired
    FileDao fileDao;


	public Map<String,Object> getAllData(String id) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("sid", userService.getSidById(id));
		map.put("carousel",  carouselService.getCarouselList());
		map.put("notice", noticeService.getNotices());
		map.put("lunbophoto", fileDao.getFileById(null, Config.INDEX_LUNBO));
		return map;
	}

}
