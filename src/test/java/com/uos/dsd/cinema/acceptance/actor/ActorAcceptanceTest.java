// package com.uos.dsd.cinema.acceptance.actor;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;

// import com.uos.dsd.cinema.acceptance.AcceptanceTest;
// import com.uos.dsd.cinema.acceptance.actor.steps.ActorSteps;
// import com.uos.dsd.cinema.acceptance.admin.steps.AdminSteps;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorCreateRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorListRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorMovieSearchRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorSearchRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorUpdateRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.response.ActorListResponse;
// import com.uos.dsd.cinema.adaptor.in.web.actor.response.ActorMovieResponse;
// import com.uos.dsd.cinema.adaptor.in.web.admin.request.AdminLoginRequest;
// import com.uos.dsd.cinema.adaptor.in.web.admin.response.AdminLoginResponse;
// import com.uos.dsd.cinema.common.exception.code.CommonResultCode;
// import com.uos.dsd.cinema.common.response.ApiResponse;
// import com.uos.dsd.cinema.domain.movie.enums.CastingType;
// import com.uos.dsd.cinema.domain.movie.enums.MovieSortType;
// import com.uos.dsd.cinema.utils.AuthHeaderProvider;

// import org.junit.jupiter.api.DisplayNameGeneration;
// import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
// import org.junit.jupiter.api.Test;

// import io.restassured.common.mapper.TypeRef;
// import io.restassured.response.Response;

// import java.util.Map;

// @DisplayNameGeneration(ReplaceUnderscores.class)
// public class ActorAcceptanceTest extends AcceptanceTest {

//     private static final String EXIST_ADMIN_USERNAME = "administrator";
//     private static final String EXIST_ADMIN_PASSWORD = "password123!";

//     private static final Long TEST_ACTOR_ID = 1L;
//     private static final Long NON_EXIST_ACTOR_ID = 999L;

//     @Test
//     public void createActor() {
//         /* Given */
//         ActorCreateRequest request = createValidActorCreateRequest();

//         /* When */
//         String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

//         Response response = ActorSteps.sendCreateActor(headers, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//         assertEquals(1L, apiResponse.data());
//     }

//     @Test
//     public void createActorWithoutAdminToken() {
//         /* Given */
//         ActorCreateRequest request = createValidActorCreateRequest();

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendCreateActor(headers, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void createActorWithInvalidToken() {
//         /* Given */
//         ActorCreateRequest request = createValidActorCreateRequest();

//         /* When */
//         String invalidToken = "invalidToken";
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

//         Response response = ActorSteps.sendCreateActor(headers, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void updateActor() {
//         /* Given */
//         ActorUpdateRequest request = createValidActorUpdateRequest();

//         /* When */
//         String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

//         Response response = ActorSteps.sendUpdateActor(headers, TEST_ACTOR_ID, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//         assertEquals(TEST_ACTOR_ID, apiResponse.data());
//     }

//     @Test
//     public void updateActorWithoutAdminToken() {
//         /* Given */
//         ActorUpdateRequest request = createValidActorUpdateRequest();

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendUpdateActor(headers, TEST_ACTOR_ID, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void updateActorWithInvalidToken() {
//         /* Given */
//         ActorUpdateRequest request = createValidActorUpdateRequest();

//         /* When */
//         String invalidToken = "invalidToken";
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

//         Response response = ActorSteps.sendUpdateActor(headers, TEST_ACTOR_ID, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<Long> apiResponse = response.as(new TypeRef<ApiResponse<Long>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void deleteActor() {
//         /* Given */
//         Long actorId = TEST_ACTOR_ID;

//         /* When */
//         String adminAccessToken = getAccessToken(EXIST_ADMIN_USERNAME, EXIST_ADMIN_PASSWORD);
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(adminAccessToken);

//         Response response = ActorSteps.sendDeleteActor(headers, actorId);
//         log.info("response: {}", response.asString());
//         ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//     }

//     @Test
//     public void deleteActorWithoutAdminToken() {
//         /* Given */
//         Long actorId = TEST_ACTOR_ID;

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendDeleteActor(headers, actorId);
//         log.info("response: {}", response.asString());
//         ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void deleteActorWithInvalidToken() {
//         /* Given */
//         Long actorId = TEST_ACTOR_ID;

//         /* When */
//         String invalidToken = "invalidToken";
//         Map<String, Object> headers = AuthHeaderProvider.createAuthorizationHeader(invalidToken);

//         Response response = ActorSteps.sendDeleteActor(headers, actorId);
//         log.info("response: {}", response.asString());
//         ApiResponse<Void> apiResponse = response.as(new TypeRef<ApiResponse<Void>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkUnauthorized(response.statusCode(), apiResponse.code());
//         assertEquals("Full authentication is required to access this resource", apiResponse.message());
//     }

//     @Test
//     public void searchActors() {
//         /* Given */
//         ActorSearchRequest request = new ActorSearchRequest("test", 0, 10);

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendSearchActors(headers, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<ActorListResponse> apiResponse = response.as(new TypeRef<ApiResponse<ActorListResponse>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//         ActorListResponse actorList = apiResponse.data();
//         assertNotNull(actorList);
//         assertEquals(2, actorList.actors().size());
//         assertEquals(2, actorList.totalCount());
//         assertEquals(0, actorList.currentPage());
//         assertEquals(1, actorList.totalPages());
//     }

//     @Test
//     public void getActorList() {
//         /* Given */
//         ActorListRequest request = new ActorListRequest(0, 10);

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendGetActorList(headers, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<ActorListResponse> apiResponse = response.as(new TypeRef<ApiResponse<ActorListResponse>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//         ActorListResponse actorList = apiResponse.data();
//         assertNotNull(actorList);
//         assertEquals(3, actorList.actors().size());
//         assertEquals(3, actorList.totalCount());
//         assertEquals(0, actorList.currentPage());
//         assertEquals(1, actorList.totalPages());
//     }

//     @Test
//     public void getMoviesByActor() {
//         /* Given */
//         Long actorId = TEST_ACTOR_ID;
//         ActorMovieSearchRequest request = new ActorMovieSearchRequest(
//             null, null, null, null, MovieSortType.POPULARITY, 0, 10
//         );

//         /* When */
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();

//         Response response = ActorSteps.sendGetMoviesByActor(headers, actorId, request);
//         log.info("response: {}", response.asString());
//         ApiResponse<ActorMovieResponse> apiResponse = response.as(new TypeRef<ApiResponse<ActorMovieResponse>>() {});
//         log.info("ApiResponse: {}", apiResponse);

//         /* Then */
//         checkSuccess(response.statusCode(), apiResponse.code());
//         ActorMovieResponse actorMovies = apiResponse.data();
//         assertNotNull(actorMovies);
//         assertEquals(3, actorMovies.movies().size());
//         assertEquals(3, actorMovies.totalCount());
//         assertEquals(0, actorMovies.currentPage());
//         assertEquals(1, actorMovies.totalPages());
        
//         // 첫 번째 영화 정보 검증
//         ActorMovieResponse.ActorMovieInfo firstMovie = actorMovies.movies().get(0);
//         assertEquals(1L, firstMovie.movieId());
//         assertEquals("주인공", firstMovie.role());
//         assertEquals(CastingType.LEAD, firstMovie.castingType());
//     }

//     private ActorCreateRequest createValidActorCreateRequest() {
//         return new ActorCreateRequest(
//             "Test Actor",
//             "test-actor.jpg"
//         );
//     }

//     private ActorUpdateRequest createValidActorUpdateRequest() {
//         return new ActorUpdateRequest(
//             "Updated Actor",
//             "updated-actor.jpg"
//         );
//     }

//     void checkSuccess(int statusCode, String code) {
//         assertEquals(200, statusCode);
//         assertEquals(CommonResultCode.SUCCESS.getCode(), code);
//     }

//     void checkUnauthorized(int statusCode, String code) {
//         assertEquals(401, statusCode);
//         assertEquals(CommonResultCode.UNAUTHORIZED.getCode(), code);
//     }

//     /**
//      * Login and return accessToken
//      */
//     private String getAccessToken(String username, String password) {
//         Map<String, Object> headers = AuthHeaderProvider.createEmptyHeader();
//         Response response = AdminSteps.sendLoginAdmin(headers, new AdminLoginRequest(username, password));
//         log.info("response: {}", response.asString());
//         ApiResponse<AdminLoginResponse> apiResponse = response.as(new TypeRef<ApiResponse<AdminLoginResponse>>() {});
//         log.info("ApiResponse: {}", apiResponse);
//         return apiResponse.data().accessToken();
//     }
// } 
