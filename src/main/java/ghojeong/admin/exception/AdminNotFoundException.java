package ghojeong.admin.exception;

import ghojeong.common.exception.NotFoundException;

public class AdminNotFoundException extends NotFoundException {
    private static final String MESSAGE = "Admin Not Found; Invalid email(%s)";

    public AdminNotFoundException(String email) {
        super(String.format(MESSAGE, email));
    }
}
