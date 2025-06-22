package com.uos.dsd.cinema.domain.customer.common.constraint;

import com.uos.dsd.cinema.domain.customer.common.exception.IllegalProfileImageException;

import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ProfileImageConstraint {

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png", "gif", "webp");
    private static final List<String> ALLOWED_MIME_TYPES = Arrays.asList(
        "image/jpeg", "image/jpg", "image/png", "image/gif", "image/webp"
    );
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024; // 2MB

    public static void validateProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return; // 프로필 이미지는 선택사항이므로 null/empty는 허용
        }

        if (!isValidProfileImage(file)) {
            throw new IllegalProfileImageException();
        }
    }

    /*
     * Checks if the profile image is valid.
     * - File size <= 2MB
     * - Allowed extensions: jpg, jpeg, png, gif, webp
     * - Allowed MIME types: image/jpeg, image/png, image/gif, image/webp
     */
    public static boolean isValidProfileImage(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            return true; // 선택사항이므로 null/empty는 유효
        }

        // 파일 크기 검증
        if (file.getSize() > MAX_FILE_SIZE) {
            return false;
        }

        // 파일 확장자 검증
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return false;
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            return false;
        }

        // MIME 타입 검증
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_MIME_TYPES.contains(contentType.toLowerCase())) {
            return false;
        }

        return true;
    }

    private static String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }
} 
