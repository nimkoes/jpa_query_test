package io.github.nimkoes.toy.repository;

import io.github.nimkoes.toy.entity.A;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ARepository extends JpaRepository<A, Long> {
}
