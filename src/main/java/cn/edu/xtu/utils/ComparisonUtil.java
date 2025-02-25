package cn.edu.xtu.utils;

import cn.edu.xtu.entity.GoogleArticle;
import cn.edu.xtu.entity.WOSArticle;

import java.util.ArrayList;
import java.util.List;

public class ComparisonUtil {
    public static List<WOSArticle> compareByTitle(List<GoogleArticle> list1, List<WOSArticle> list2) {
        List<WOSArticle> result = new ArrayList<>();

        for (WOSArticle wosArticle : list2){
            boolean flag = true;
            for (GoogleArticle googleArticle : list1){
                if (googleArticle.getTitle().equals(wosArticle.getTitle())) {
                    flag = false;
                    break;
                }
            }
            if (flag){
                result.add(wosArticle);
            }
        }
        return result;
    }
}
