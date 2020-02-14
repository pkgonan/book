package io.book.search.domain;

public class Metadata {

    private long total;

    private Metadata() {}

    public static Metadata of(long total) {
        Metadata metadata = new Metadata();
        metadata.total = total;

        return metadata;
    }

    public long getTotal() {
        return total;
    }
}