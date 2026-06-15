package com.exemplo.cursoservice.repository;

public class DisciplinaRepository {
    
}
package com.exemplo.crudmongo.repository;

import com.exemplo.crudmongo.Model.Disciplina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DisciplinaRepository extends JpaRepository<Disciplina, Long> {
}