package contactSite.programmer.dto;

import contactSite.programmer.dto.read.ProgrammerReadResponse;

import java.util.List;

public record PageResponse(
        int totalPages,
        Long totalCount,
        int currentPage,
        int pageSize,
        List<ProgrammerReadResponse> programmerReadResponses
) {
}
