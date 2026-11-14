package pubgrecord.repository;

import java.util.List;
import pubgrecord.domain.Weapon;
import pubgrecord.domain.GameMatch;

public interface GameMatchRepository {

    GameMatch save(GameMatch gameMatch);

    GameMatch findById(Long id);

    List<GameMatch> findAll();

    void deleteById(Long id);

}
