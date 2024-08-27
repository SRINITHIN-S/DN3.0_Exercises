package com.example.bookstoreapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDTO {

    private Long id;

    @NotBlank(message = "Title is mandatory")
    @Size(max = 255, message = "Title can have a maximum of 255 characters")
    private String title;

    @NotBlank(message = "Author is mandatory")
    @Size(max = 255, message = "Author can have a maximum of 255 characters")
    private String author;

    @NotNull(message = "Price is mandatory")
    @Min(value = 0, message = "Price must be greater than or equal to 0")
    private Double price;

    @NotBlank(message = "ISBN is mandatory")
    @Size(max = 13, message = "ISBN can have a maximum of 13 characters")
    private String isbn;
}
