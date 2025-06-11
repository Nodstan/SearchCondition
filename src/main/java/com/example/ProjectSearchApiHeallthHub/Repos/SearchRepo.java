package com.example.ProjectSearchApiHeallthHub.Repos;

import com.example.ProjectSearchApiHeallthHub.Model.Search;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface SearchRepo extends MongoRepository<Search, String> {

    // ✅ New method for case-insensitive exact name match
    Optional<Search> findByNameIgnoreCase(String name);

    // Smart and keyword search methods
    List<Search> findByNameContainingIgnoreCaseOrDefinitionContainingIgnoreCaseOrOverviewContainingIgnoreCaseOrSymptomsContainingIgnoreCase(
            String name, String def, String overview, String symptoms
    );

    List<Search> findByNameContainingIgnoreCaseOrDefinitionContainingIgnoreCaseOrOverviewContainingIgnoreCase(
            String name, String def, String overview
    );
}
