package pl.otwartemigawki.OtwarteMigawkiApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.otwartemigawki.OtwarteMigawkiApp.model.Todo;

public interface TodoRepository extends JpaRepository<Todo, Long>{
}
