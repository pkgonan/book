package io.book.common.util;

import java.util.UUID;

public class TrackingIdGenerator {

    private TrackingIdGenerator() {
        //Nothing
    }

    public static String generateTrackingId() {
        return UUID.randomUUID().toString();
    }
}