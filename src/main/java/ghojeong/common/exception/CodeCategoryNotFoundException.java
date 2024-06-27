package ghojeong.common.exception;

public class CodeCategoryNotFoundException extends UnavailableException {
    private static final String MESSAGE = "Code Category Not Found: %s";

    public CodeCategoryNotFoundException(Class<?> clazz) {
        super(String.format(MESSAGE, clazz.getName()));
    }
}
