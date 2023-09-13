package com.maverik.test.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@Document(indexName = "movies")
public class Movie {

    @Id
    @JsonProperty("imdbId")
    private String imdbId;
    @JsonProperty("title")
    private String title;
    @JsonProperty("year")
    private String year;
    @JsonProperty("rated")
    private String rated;
    @JsonProperty("released")
    private String released;
    @JsonProperty("runtime")
    private String runtime;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("director")
    private String director;
    @JsonProperty("actors")
    private List<String> actors;
    @JsonProperty("plot")
    private String plot;
    @JsonProperty("language")
    private String language;

    @JsonProperty("country")
    private String country;
    @JsonProperty("poster")
    private String poster;
    @JsonProperty("imdbRating")
    private String imdbRating;

    @JsonProperty("owner")
    private String owner;


}
