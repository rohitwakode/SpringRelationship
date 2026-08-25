package com.Lucifer.newRelationship.exception;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        LocalDateTime timestamp,
        int status,
        String error,
        Object message,
        String path
) {
}
