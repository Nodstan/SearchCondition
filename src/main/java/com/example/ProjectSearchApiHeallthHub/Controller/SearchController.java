package com.example.ProjectSearchApiHeallthHub.Controller;

import com.example.ProjectSearchApiHeallthHub.Model.Search;
import com.example.ProjectSearchApiHeallthHub.Repos.SearchRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/searches")
@CrossOrigin
public class SearchController {

    @Autowired
    private SearchRepo searchRepo;

    // Create a new Search
    @PostMapping("/create")
    public ResponseEntity<Search> createSearch(@RequestBody Search search) {
        Search newSearch = searchRepo.save(search);
        return ResponseEntity.ok(newSearch);
    }

    // Create multiple Searches
    @PostMapping("/createAll")
    public ResponseEntity<List<Search>> createAllSearches(@RequestBody List<Search> searches) {
        List<Search> savedSearches = searchRepo.saveAll(searches);
        return ResponseEntity.ok(savedSearches);
    }

    // Get all Searches
    @GetMapping("/getAll")
    public List<Search> getAllSearches() {
        return searchRepo.findAll();
    }

    // ✅ Get Search by Name (case-insensitive)
    @GetMapping("/get/{name}")
    public ResponseEntity<Search> getSearchByName(@PathVariable("name") String name) {
        Optional<Search> search = searchRepo.findByNameIgnoreCase(name);  // Use case-insensitive search
        if (search.isPresent()) {
            return ResponseEntity.ok(search.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Smart search by multiple keywords (split)
    @GetMapping("/smart-search")
    public ResponseEntity<List<Search>> smartSearch(@RequestParam("q") String input) {
        String[] keywords = input.toLowerCase().split("\\W+");
        List<Search> results = new ArrayList<>();

        for (String keyword : keywords) {
            List<Search> partialResults = searchRepo.findByNameContainingIgnoreCaseOrDefinitionContainingIgnoreCaseOrOverviewContainingIgnoreCaseOrSymptomsContainingIgnoreCase(
                    keyword, keyword, keyword, keyword
            );

            for (Search result : partialResults) {
                if (!results.contains(result)) {
                    results.add(result);
                }
            }
        }

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    // ✅ Real-time name-based search by characters typed
    @GetMapping("/search")
    public ResponseEntity<List<Search>> searchByKeyword(@RequestParam("q") String keyword) {
        if (keyword.trim().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        List<Search> results = searchRepo.findByNameContainingIgnoreCaseOrDefinitionContainingIgnoreCaseOrOverviewContainingIgnoreCaseOrSymptomsContainingIgnoreCase(
                keyword, keyword, keyword, keyword
        );

        if (results.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(results);
    }

    // Update an existing Search
    @PutMapping("/update/{name}")
    public ResponseEntity<Search> updateSearch(@PathVariable("name") String name, @RequestBody Search searchDetails) {
        Optional<Search> searchOptional = searchRepo.findByNameIgnoreCase(name);
        if (searchOptional.isPresent()) {
            Search search = searchOptional.get();
            search.setName(searchDetails.getName());
            search.setDefinition(searchDetails.getDefinition());
            search.setOverview(searchDetails.getOverview());
            search.setPrevention(searchDetails.getPrevention());
            search.setTreatment(searchDetails.getTreatment());
            search.setManagement(searchDetails.getManagement());
            search.setSymptoms(searchDetails.getSymptoms());

            Search updatedSearch = searchRepo.save(search);
            return ResponseEntity.ok(updatedSearch);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a Search by name (case-insensitive)
    @DeleteMapping("/delete/{name}")
    public ResponseEntity<Void> deleteSearch(@PathVariable("name") String name) {
        Optional<Search> searchOptional = searchRepo.findByNameIgnoreCase(name);
        if (searchOptional.isPresent()) {
            searchRepo.delete(searchOptional.get());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
