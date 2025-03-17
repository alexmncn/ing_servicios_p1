package com.alexmncn.ing_servicios_p1.daos;

import org.springframework.stereotype.Repository;

@Repository
public class ArticleDAOAPI implements ArticleDAOInterface {
    String APIRoute = "https://api.tiendafleming.es/articles/featured";

    @Override
    public void getFeaturedArticles() {

    }
}
