// package com.uos.dsd.cinema.acceptance.actor.steps;

// import static io.restassured.RestAssured.given;

// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorCreateRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorListRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorMovieSearchRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorSearchRequest;
// import com.uos.dsd.cinema.adaptor.in.web.actor.request.ActorUpdateRequest;

// import io.restassured.http.ContentType;
// import io.restassured.response.Response;

// import java.util.HashMap;
// import java.util.Map;

// public class ActorSteps {

//     private static final String ACTOR_CREATE_URL = "/actors";
//     private static final String ACTOR_UPDATE_URL = "/actors/{id}";
//     private static final String ACTOR_DELETE_URL = "/actors/{id}";
//     private static final String ACTOR_SEARCH_URL = "/actors/search";
//     private static final String ACTOR_LIST_URL = "/actors";
//     private static final String ACTOR_MOVIES_URL = "/actors/{id}/movies";

//     public static Response sendCreateActor(
//             Map<String, Object> headers,
//             ActorCreateRequest request) {

//         return given().log().all()
//                 .headers(headers)
//                 .contentType(ContentType.JSON)
//                 .body(request)
//             .when()
//                 .post(ACTOR_CREATE_URL)
//             .then().log().all()
//                 .extract()
//                 .response();
//     }

//     public static Response sendUpdateActor(
//             Map<String, Object> headers,
//             Long id,
//             ActorUpdateRequest request) {

//         return given().log().all()
//                 .headers(headers)
//                 .contentType(ContentType.JSON)
//                 .body(request)
//             .when()
//                 .put(ACTOR_UPDATE_URL.replace("{id}", id.toString()))
//             .then().log().all()
//                 .extract()
//                 .response();
//     }

//     public static Response sendDeleteActor(
//             Map<String, Object> headers,
//             Long id) {

//         return given().log().all()
//                 .headers(headers)
//             .when()
//                 .delete(ACTOR_DELETE_URL.replace("{id}", id.toString()))
//             .then().log().all()
//                 .extract()
//                 .response();
//     }

//     public static Response sendSearchActors(
//             Map<String, Object> headers,
//             ActorSearchRequest request) {

//         Map<String, Object> queryParams = new HashMap<>();
//         if (request.query() != null) queryParams.put("query", request.query());
//         if (request.page() != null) queryParams.put("page", request.page());
//         if (request.size() != null) queryParams.put("size", request.size());

//         return given().log().all()
//                 .headers(headers)
//                 .queryParams(queryParams)
//             .when()
//                 .get(ACTOR_SEARCH_URL)
//             .then()
//                 .log().all()
//                 .extract()
//                 .response();
//     }

//     public static Response sendGetActorList(
//             Map<String, Object> headers,
//             ActorListRequest request) {

//         Map<String, Object> queryParams = new HashMap<>();
//         if (request.page() != null) queryParams.put("page", request.page());
//         if (request.size() != null) queryParams.put("size", request.size());

//         return given().log().all()
//                 .headers(headers)
//                 .queryParams(queryParams)
//             .when()
//                 .get(ACTOR_LIST_URL)
//             .then().log().all()
//                 .extract()
//                 .response();
//     }

//     public static Response sendGetMoviesByActor(
//             Map<String, Object> headers,
//             Long id,
//             ActorMovieSearchRequest request) {

//         Map<String, Object> queryParams = new HashMap<>();
//         if (request.startDate() != null) queryParams.put("startDate", request.startDate().toString());
//         if (request.endDate() != null) queryParams.put("endDate", request.endDate().toString());
//         if (request.genres() != null) queryParams.put("genres", request.genres());
//         if (request.screenTypes() != null) queryParams.put("screenTypes", request.screenTypes());
//         if (request.sortBy() != null) queryParams.put("sortBy", request.sortBy());
//         if (request.page() != null) queryParams.put("page", request.page());
//         if (request.size() != null) queryParams.put("size", request.size());

//         return given().log().all()
//                 .headers(headers)
//                 .queryParams(queryParams)
//             .when()
//                 .get(ACTOR_MOVIES_URL.replace("{id}", id.toString()))
//             .then().log().all()
//                 .extract()
//                 .response();
//     }
// } 
