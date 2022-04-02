package net.asg.games.yokel.persistence;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import net.asg.games.yokel.objects.YokelPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface YokelPlayerRepository extends YokelRepository<YokelPlayer, String> {
}