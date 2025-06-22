package com.uos.dsd.cinema.application.service.actor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.adaptor.out.persistence.actor.ActorJpaRepository;
import org.springframework.data.jpa.domain.Specification;
import com.uos.dsd.cinema.domain.actor.Actor;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActorService {

    private final ActorJpaRepository actorJpaRepository;

    public Page<Actor> getActorList(String name, Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Actor> spec = Specification.where(ActorSpecification.notDeleted());
        if (name != null) {
            spec = spec.and(ActorSpecification.hasName(name));
        }
        return actorJpaRepository.findAll(spec, pageable);
    }
}
