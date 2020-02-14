package io.book.search.domain;

import com.google.common.collect.Lists;

abstract class SearchTest {

    protected static Document generateDocument() {
        return Document.of("book", "test", "http://test.com", "ISBN", "PublishedAt", Lists.newArrayList(), "publisher", Lists.newArrayList(), 10000, 5000, "thumbnail","SOLD_OUT");
    }
}
