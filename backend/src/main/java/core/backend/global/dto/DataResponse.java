package core.backend.global.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class DataResponse {
    private int count;
    private List<?> data;

    @Builder
    public DataResponse(int count, List<?> data) {
        this.count = count;
        this.data = data;
    }
}
