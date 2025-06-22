package com.uos.dsd.cinema.adaptor.out.persistence.actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uos.dsd.cinema.domain.actor.Actor;

public interface ActorJpaRepository
        extends JpaRepository<Actor, Long>, JpaSpecificationExecutor<Actor> {

}
