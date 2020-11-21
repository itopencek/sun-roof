package cz.cvut.fit.havasiva.repository;

import cz.cvut.fit.havasiva.entity.Branch;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BranchRepository extends JpaRepository<Branch, Integer> {

    Optional<Branch> findByCountry(String country);
}
