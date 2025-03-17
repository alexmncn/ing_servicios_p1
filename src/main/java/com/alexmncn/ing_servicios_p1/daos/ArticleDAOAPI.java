package com.alexmncn.ing_servicios_p1.daos;

import com.alexmncn.ing_servicios_p1.dtos.ArticleDTO;
import com.alexmncn.ing_servicios_p1.dtos.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Repository
public class ArticleDAOAPI implements ArticleDAOInterface {
    private final WebClient webClient = WebClient.create(); // Initializa webclient
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<ArticleDTO> mapToArticleList(String json) {
        // Return a list of ArticleDTO form JSON
        try {
            return objectMapper.readValue(json, objectMapper.getTypeFactory().constructCollectionType(List.class, ArticleDTO.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error al mapear JSON a Lista de ArticleDTO", e);
        }
    }

    @Override
    public List<ArticleDTO> getFeaturedArticles() {
        String APIRoute = "https://api.tiendafleming.es/articles/featured";

        // Make request
        String response = webClient.get()
                .uri(APIRoute)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return mapToArticleList(response);
    }

    @Override
    public List<ArticleDTO> getNewArticles() {
        String APIRoute = "https://api.tiendafleming.es/articles/new";

        // Make request
        String response = webClient.get()
                .uri(APIRoute)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        return mapToArticleList(response);
    }
}
