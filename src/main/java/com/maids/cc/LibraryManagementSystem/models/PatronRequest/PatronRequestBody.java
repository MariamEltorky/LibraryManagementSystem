package com.maids.cc.LibraryManagementSystem.models.PatronRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PatronRequestBody {

    @JsonProperty("id")
    @NotNull
    private int id;

    @JsonProperty("name")
    @NotNull
    @Pattern(regexp = "^(?!\\s*$).+", message = "Title cannot be empty")
    private String name;

    @JsonProperty("contactInformation")
    @NotNull
    private String contactInformation;

}
