package mx.wizelinehack.contextenginedemo.domain.document;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Armando Montoya,
 * created on: 6/2/18
 */
@Repository
public interface DocumentRepo extends JpaRepository<Document, Long> {
}
