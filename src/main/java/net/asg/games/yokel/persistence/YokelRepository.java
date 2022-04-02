package net.asg.games.yokel.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface YokelRepository<T, ID> extends JpaRepository<T, ID> {
    T findByName(ID name);
}