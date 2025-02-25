package cn.edu.xtu.entity;

import lombok.*;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OutputArticle extends Article{
    private List<Article> googleArticles;
}
