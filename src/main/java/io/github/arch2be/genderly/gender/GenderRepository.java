package io.github.arch2be.genderly.gender;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository extends JpaRepository<GenderToken, Long> {
    Optional<GenderToken> findByName(String name);

    List<GenderToken> findByNameIn(List<String> nameList);
}
