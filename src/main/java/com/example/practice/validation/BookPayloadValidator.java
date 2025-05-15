package com.example.practice.validation;


import com.example.practice.payload.BookDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class BookPayloadValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return BookDto.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        BookDto bookDto = (BookDto) target;
        ObjectMapper mapper = new ObjectMapper();
        try {
            System.out.println(mapper.writeValueAsString(bookDto));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }

            if (bookDto.getAuthor() == null || bookDto.getAuthor().trim().isEmpty()) {
                errors.rejectValue("author", "field.required", "Author is required");
            }

            if (bookDto.getTitle() == null || bookDto.getTitle().trim().isEmpty()) {
                errors.rejectValue("title", "field.required", "Title is required");
            }
    }
}
