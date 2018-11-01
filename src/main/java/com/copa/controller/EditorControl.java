package com.copa.controller;

import com.copa.service.CategoryService;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 文章编辑Controller
 */
@Controller
public class EditorControl {

    private Logger logger = LoggerFactory.getLogger(EditorControl.class);

    @Autowired
    private CategoryService categoryService;

    /**
     * 获得所有的分类
     * @return
     */
    @GetMapping("/findCategoriesName")
    @ResponseBody
    public JSONArray findCategoriesName(){
        return categoryService.findCategoriesName();
    }

}
