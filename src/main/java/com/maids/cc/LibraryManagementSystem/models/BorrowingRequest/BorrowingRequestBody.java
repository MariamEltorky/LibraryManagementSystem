package com.maids.cc.LibraryManagementSystem.models.BorrowingRequest;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.sql.Date;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BorrowingRequestBody {

    @JsonProperty("id")
    @NotNull
    private int id;

    @JsonProperty("bookID")
    @NotNull
    private int bookID;

    @JsonProperty("patronID")
    @NotNull
    private int patronID;

    @JsonProperty("borrowingDate")
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Please use yyyy-MM-dd")
    private String borrowingDate;

    @JsonProperty("returnDate")
    @NotNull
    @Pattern(regexp = "\\d{4}-\\d{2}-\\d{2}", message = "Invalid date format. Please use yyyy-MM-dd")
    private String returnDate;


}
