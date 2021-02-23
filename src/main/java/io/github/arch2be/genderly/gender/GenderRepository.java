package io.github.arch2be.genderly.gender;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;

public interface GenderRepository extends PagingAndSortingRepository<GenderToken, Integer> {
    Optional<GenderToken> findByName(String name);

    List<GenderToken> findByNameIn(List<String> nameList);

    List<GenderToken> findAllByGenderType(GenderType genderType, Pageable pageable);
}
