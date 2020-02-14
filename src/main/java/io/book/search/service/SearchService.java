package io.book.search.service;

import io.book.search.domain.Document;
import io.book.search.domain.SearchExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final SearchExecutor searchExecutor;

    SearchService(final SearchExecutor searchExecutor) {
        this.searchExecutor = searchExecutor;
    }

    public Page<DocumentDto> search(final String query, final Pageable pageable) {
        return toDocumentDtoPage(searchExecutor.execute(query, pageable));
    }

    private Page<DocumentDto> toDocumentDtoPage(final Page<Document> documentPage) {
        return documentPage.map(this::toDocumentDto);
    }

    private DocumentDto toDocumentDto(final Document document) {
        DocumentDto documentDto = new DocumentDto();
        documentDto.setTitle(document.getTitle());
        documentDto.setDescription(document.getDescription());
        documentDto.setUrl(document.getUrl());
        documentDto.setIsbn(document.getIsbn());
        documentDto.setPublishAt(document.getPublishAt());
        documentDto.setAuthors(document.getAuthors());
        documentDto.setPublisher(document.getPublisher());
        documentDto.setTranslators(document.getTranslators());
        documentDto.setPrice(document.getPrice());
        documentDto.setSalePrice(document.getSalePrice());
        documentDto.setThumbnail(document.getThumbnail());
        documentDto.setStatus(document.getStatus());

        return documentDto;
    }
}