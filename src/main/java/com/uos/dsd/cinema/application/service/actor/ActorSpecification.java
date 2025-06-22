package com.uos.dsd.cinema.application.service.actor;

import org.springframework.data.jpa.domain.Specification;

import com.uos.dsd.cinema.domain.actor.Actor;

public class ActorSpecification {

    public static Specification<Actor> notDeleted() {
        return (root, query, criteriaBuilder) -> criteriaBuilder.isNull(root.get("deletedAt"));
    }

    public static Specification<Actor> hasName(String name) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("name"),
                "%" + name + "%");
    }
}