package com.example.bookstoreapi.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JacksonXmlRootElement(localName = "Customer")
@JsonPropertyOrder({ "id", "name", "email", "address" })
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255, message = "Name can have a maximum of 255 characters")
    private String name;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Size(max = 255, message = "Email can have a maximum of 255 characters")
    private String email;

    @Size(max = 255, message = "Address can have a maximum of 255 characters")
    private String address;
}
