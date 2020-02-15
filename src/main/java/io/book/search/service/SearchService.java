package io.book.search.service;

import io.book.common.service.EventPublisherService;
import io.book.search.domain.Document;
import io.book.search.domain.SearchExecuted;
import io.book.search.domain.SearchExecutor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SearchService extends EventPublisherService {

    private final SearchExecutor searchExecutor;

    SearchService(final SearchExecutor searchExecutor) {
        this.searchExecutor = searchExecutor;
    }

    public Page<DocumentDto> search(final long memberId, final String query, final Pageable pageable) {
        final SearchExecuted event = searchExecutor.execute(memberId, query, pageable);

        publishEvent(event);
        return toDocumentDtoPage(event.getDocuments());
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