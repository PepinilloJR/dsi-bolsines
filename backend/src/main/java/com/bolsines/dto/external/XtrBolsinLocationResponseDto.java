package com.bolsines.dto.external;

import java.util.List;

public record XtrBolsinLocationResponseDto(
        List<XtrBolsinLocationItemDto> items
) {
}