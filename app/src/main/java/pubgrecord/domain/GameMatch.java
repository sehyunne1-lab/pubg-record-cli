package pubgrecord.domain;

import java.time.LocalDate;
import java.util.List;

public class GameMatch {

        private final Long id;
        private final LocalDate date;
        private final List<WeaponDamage> weaponDamages;

        public GameMatch(Long id, LocalDate date, List<WeaponDamage> weaponDamages) {
            this.id = id;
            this.date = date;
            this.weaponDamages = List.copyOf(weaponDamages);
        }

        public Long getId() {
            return id;
        }

        public LocalDate getDate() {
            return date;
        }

        public List<WeaponDamage> getWeaponDamages() {
            return weaponDamages;
        }

    }