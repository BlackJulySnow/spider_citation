package cn.edu.xtu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ArticleCitation extends Article{
    private Set<String> citations;
}
