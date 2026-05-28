package com.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequest {
    private String searchTerm;
    Long genreId;
    Boolean availableOnly;
    Integer page;
    Integer size;
    String sortBy="createdAt";
    String sortDirection="DESC";
}
