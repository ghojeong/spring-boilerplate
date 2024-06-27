package ghojeong.common.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ListResponse<T> extends CommonResponse<List<T>> {
    private final Integer count;
    private final Integer totalCount;

    public ListResponse(HttpStatus httpStatus, String message, List<T> data, Integer totalCount) {
        super(httpStatus, message, data);
        this.count = data.size();
        this.totalCount = totalCount;
    }

    public ListResponse(HttpStatus httpStatus, String message, List<T> data) {
        super(httpStatus, message, data);
        this.count = data.size();
        this.totalCount = data.size();
    }
}
