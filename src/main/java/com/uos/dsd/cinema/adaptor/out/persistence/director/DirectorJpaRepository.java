package com.uos.dsd.cinema.adaptor.out.persistence.director;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.uos.dsd.cinema.domain.director.Director;

public interface DirectorJpaRepository
        extends JpaRepository<Director, Long>, JpaSpecificationExecutor<Director> {

}
