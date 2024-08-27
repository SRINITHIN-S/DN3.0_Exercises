package com.example.bookstoreapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer
            // Do not use path extensions (.json, .xml) for content negotiation
            .favorPathExtension(false)
            // Do not use query parameters for content negotiation
            .favorParameter(false)
            // Use the Accept header to determine the response format
            .ignoreAcceptHeader(false)
            // Set default content type to JSON
            .defaultContentType(MediaType.APPLICATION_JSON)
            // Define supported media types
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Add XML message converter
        converters.add(new MappingJackson2XmlHttpMessageConverter());
    }
}
