package com.swe573.socialhub.repository;

import com.swe573.socialhub.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {
}