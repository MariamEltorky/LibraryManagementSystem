package com.maids.cc.LibraryManagementSystem.models.BookRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maids.cc.LibraryManagementSystem.models.JPA.Patron;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookRequestBody {
    @JsonProperty("id")
    @NotNull
    private int id;

    @JsonProperty("title")
    @NotNull
    @Pattern(regexp = "^(?!\\s*$).+", message = "Title cannot be empty")
    private String title;

    @JsonProperty("author")
    @NotNull
    @Pattern(regexp = "^(?!\\s*$).+", message = "Author cannot be empty")
    private String author;

    @JsonProperty("publicationYear")
    @NotNull
    @Pattern(regexp = "\\d{4}", message = "Invalid Year Length")
    private String publicationYear;

    @JsonProperty("ISBN")
    @NotNull
    @Pattern(regexp = "\\d{3}-\\d{10}", message = "Invalid ISBN format")
    private String ISBN;

    @JsonProperty("borrowed")
    @NotNull
    private int borrowed;

    @JsonProperty("patron")
    private Patron patron;
}
