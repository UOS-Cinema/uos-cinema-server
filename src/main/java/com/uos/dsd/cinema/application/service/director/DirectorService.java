package com.uos.dsd.cinema.application.service.director;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.uos.dsd.cinema.adaptor.out.persistence.director.DirectorJpaRepository;
import org.springframework.data.jpa.domain.Specification;
import com.uos.dsd.cinema.domain.director.Director;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DirectorService {

    private final DirectorJpaRepository directorJpaRepository;

    public Page<Director> getDirectorList(String name, Integer page, Integer size) {

        Sort sort = Sort.by(Sort.Direction.ASC, "name");
        Pageable pageable = PageRequest.of(page, size, sort);
        Specification<Director> spec = Specification.where(DirectorSpecification.notDeleted());
        if (name != null) {
            spec = spec.and(DirectorSpecification.hasName(name));
        }
        return directorJpaRepository.findAll(spec, pageable);
    }
}
