package net.asg.games.yokel.persistence;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete

import net.asg.games.yokel.objects.YokelSeat;
import org.springframework.stereotype.Repository;

@Repository
public interface YokelSeatRepository extends YokelRepository<YokelSeat, String> {
}