package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.ArticleDTO;

import java.util.List;

public interface ArticleDAOInterface {
    public List<ArticleDTO> getFeaturedArticles();

    public List<ArticleDTO> getNewArticles();
}
