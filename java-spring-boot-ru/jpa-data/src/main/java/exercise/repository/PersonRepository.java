package exercise.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import exercise.model.Person;

// BEGIN
public interface PersonRepository extends JpaRepository<Person, Long> {
}
// END
