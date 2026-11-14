package pubgrecord.repository;

import java.util.List;
import pubgrecord.domain.Weapon;

public interface WeaponRepository {
    Weapon save(Weapon Weapon);
    Weapon findById(Long id);
    Weapon findByName(String name);
    List<Weapon> findAll();
    void deleteById(Long id);
}
