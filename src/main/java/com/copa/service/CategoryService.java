package com.copa.service;

import net.sf.json.JSONArray;
import org.springframework.stereotype.Service;

/**
 * 分类业务操作
 */
public interface CategoryService {

    /**
     * 获得所有的分类
     * @return
     */
    JSONArray findCategoriesName();
}
