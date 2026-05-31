package com.project.payload.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookSearchRequest {
    private String searchTerm;
    private Long genreId;
    private Boolean availableOnly;
    private Integer page;
    private Integer size;
    private String sortBy="createdAt";
    private String sortDirection="DESC";
}
