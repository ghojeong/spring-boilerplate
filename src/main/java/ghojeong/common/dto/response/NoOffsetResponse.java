package ghojeong.common.dto.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class NoOffsetResponse<T> extends CommonResponse<List<T>> {
    private final Integer count;
    private final Integer totalCount;
    private final Long globalMaxSeq;
    private final Long globalMinSeq;

    public NoOffsetResponse(
            HttpStatus httpStatus,
            String message,
            List<T> data,
            Integer totalCount,
            Long globalMaxSeq,
            Long globalMinSeq
    ) {
        super(httpStatus, message, data);
        this.count = data.size();
        this.totalCount = totalCount;
        this.globalMaxSeq = globalMaxSeq;
        this.globalMinSeq = globalMinSeq;
    }
}
