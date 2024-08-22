package com.example.bookstoreapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonValue;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.text.DecimalFormat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "id", "title", "author", "price", "isbn" }) // Order of JSON properties
public class BookDTO {

    private Long id;
    private String title;
    private String author;

    @JsonSerialize(using = CustomPriceSerializer.class) // Custom serializer
    private double price;

    private String isbn;
}

class CustomPriceSerializer extends com.fasterxml.jackson.databind.JsonSerializer<Double> {
    private static final DecimalFormat formatter = new DecimalFormat("#0.00");

    @Override
    public void serialize(Double value, com.fasterxml.jackson.core.JsonGenerator gen,
                          com.fasterxml.jackson.databind.SerializerProvider serializers)
            throws java.io.IOException {
        gen.writeString(formatter.format(value));
    }
}
