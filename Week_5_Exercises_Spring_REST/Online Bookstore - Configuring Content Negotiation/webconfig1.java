package com.example.bookstoreapi.config;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.hateoas.Jackson2HalModule;
import org.springframework.context.annotation.Bean;
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
            .favorPathExtension(false)
            .favorParameter(false)
            .ignoreAcceptHeader(false)
            .defaultContentType(MediaType.APPLICATION_JSON)
            .mediaType("json", MediaType.APPLICATION_JSON)
            .mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // Configure JSON message converter (already present by default)
        // Configure XML message converter with HATEOAS support
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.registerModule(new Jackson2HalModule());
        xmlMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        MappingJackson2XmlHttpMessageConverter xmlConverter = new MappingJackson2XmlHttpMessageConverter(xmlMapper);
        converters.add(xmlConverter);
    }
}
