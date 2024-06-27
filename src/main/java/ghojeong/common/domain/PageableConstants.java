package ghojeong.common.domain;

import org.springframework.data.domain.Pageable;

public final class PageableConstants {
    public static final Pageable SINGLE_PAGEABLE = Pageable.ofSize(1);
    public static final Pageable BIG_PAGEABLE = Pageable.ofSize(300);
    public static final Pageable DEFAULT_PAGEABLE = Pageable.ofSize(20);

    private PageableConstants() {}
}
