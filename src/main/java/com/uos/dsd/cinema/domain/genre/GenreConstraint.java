package com.uos.dsd.cinema.domain.genre;

public class GenreConstraint {

    public static final String NAME_REGEX = "^[a-zA-Z0-9]+$";
    public static final int NAME_MAX_LENGTH = 20;
    public static final int DESCRIPTION_MAX_LENGTH = 100;
    public static final int IMAGE_URL_MAX_LENGTH = 255;

    public static void validateName(String name) {

        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(GenreExceptionCode.INVALID_NAME.getMessage());
        }
        if (name.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException(GenreExceptionCode.INVALID_NAME_LENGTH.getMessage());
        }
    }

    public static void validateDescription(String description) {

        if (description.length() > DESCRIPTION_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    GenreExceptionCode.INVALID_DESCRIPTION_LENGTH.getMessage());
        }
    }

    public static void validateImageUrl(String imageUrl) {

        if (imageUrl.length() > IMAGE_URL_MAX_LENGTH) {
            throw new IllegalArgumentException(
                    GenreExceptionCode.INVALID_IMAGE_URL_LENGTH.getMessage());
        }
    }
}
