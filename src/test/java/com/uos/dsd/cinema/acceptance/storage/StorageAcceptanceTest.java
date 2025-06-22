package com.uos.dsd.cinema.acceptance.storage;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.uos.dsd.cinema.acceptance.AcceptanceTest;
import com.uos.dsd.cinema.application.port.out.storage.Storage;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

@DisplayNameGeneration(ReplaceUnderscores.class)
public class StorageAcceptanceTest extends AcceptanceTest {

    @Autowired
    private Storage storage;
    private String fileName = "test.txt";
    private String uploadPath = "test/acceptance";
    
    @LocalServerPort
    private int port;

    @Test
    public void saveAndDownload() throws IOException {

        /* Given */
        String filePath = Path.of(uploadPath, fileName).toString();
        String fileContent = "Hello, Storage Test!";
        MultipartFile file = new MockMultipartFile(
                fileName,
                fileName,
                "text/plain",
                fileContent.getBytes(StandardCharsets.UTF_8));

        /* When */
        // 1. Storage에 파일 업로드
        storage.upload(filePath, file);

        // 2. Storage에서 파일 URL 획득
        String downloadPath = storage.getUrl(filePath);
        log.info("downloadPath: {}", downloadPath);

        URI uri = URI.create(downloadPath);
        String scheme = uri.getScheme();
        String host = uri.getHost();
        String port = String.valueOf(uri.getPort() == -1 ? (uri.getScheme().equals("https") ? 443 : 80) : uri.getPort());
        // 테스트 환경에서 로컬 포트는 0으로 설정되어 있음.
        // 로컬 머신에 요청을 보내기 위해서 포트 번호를 실제 사용중인 포트 번호로 변경
        if (port.equals("0")) {
            port = String.valueOf(this.port);
        }
        String path = uri.getPath();
        String query = uri.getQuery();

        // 3. GET 요청으로 파일 다운로드 시도
        RestAssured.baseURI = scheme + "://" + host;
        RestAssured.port = Integer.parseInt(port);
        Response response = given().log().all()
                .when()
                    .get(path + (query != null ? "?" + query : ""))
                .then().log().all()
                    .extract().response();

        /* Then */
        // HTTP 응답 상태 코드가 200이어야 함
        assertEquals(200, response.statusCode());

        // 다운로드한 파일 내용이 원본과 동일해야 함
        String downloadedContent = response.body().asString();
        assertEquals(fileContent, downloadedContent);
    }
}
