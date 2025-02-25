package cn.edu.xtu.service.impl;

import cn.edu.xtu.entity.WOSArticle;
import cn.edu.xtu.service.Spider;

import java.util.List;

public class WOSImpl extends Spider<WOSArticle> {
    @Override
    public List<WOSArticle> search(String paperTitle) {
        return null;
    }

    @Override
    public void getPage() {

    }
}
