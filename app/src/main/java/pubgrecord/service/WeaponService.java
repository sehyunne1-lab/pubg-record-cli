package pubgrecord.service;

import java.util.List;
import pubgrecord.domain.GameMatch;
import pubgrecord.domain.Weapon;
import pubgrecord.domain.WeaponDamage;
import pubgrecord.domain.WeaponType;
import pubgrecord.repository.GameMatchRepository;
import pubgrecord.repository.WeaponRepository;

public class WeaponService {

    // 필드 2개를 만든다
    // - 무기 저장소: 타입은 WeaponRepository
    // - 판 기록 저장소: 타입은 GameMatchRepository
    // - 둘 다 private final (한 번 넣으면 바꿀 일이 없음)
    private final WeaponRepository weaponRepository;
    private final GameMatchRepository gameMatchRepository;

    // 생성자를 만든다
    // - 저장소 두 개를 매개변수로 받아서 위의 필드에 넣는다
    // - 이렇게 밖에서 받아 오는 걸 "생성자 주입"이라고 한다
    public WeaponService(WeaponRepository weaponRepository,GameMatchRepository gameMatchRepository){
        this.weaponRepository = weaponRepository;
        this.gameMatchRepository = gameMatchRepository;
    }

    public Weapon register(String name, WeaponType type) {
        // 1. 이름 검사: name이 null이거나 공백뿐이면 예외를 던진다
        //    - null인지 먼저 확인해야 한다 (null에 메서드를 부르면 프로그램이 터짐)
        //    - 공백뿐인지는 isBlank()로 확인한다
        //    - type이 null인 경우도 같은 if에서 함께 막는다
        //    - 예외: throw new IllegalArgumentException("메시지")
        if(name == null || name.isBlank() || type == null){
            throw new IllegalArgumentException("무기 이름과 종류는 필수입니다");
        }
        // 2. 이름 앞뒤의 공백을 strip()으로 지우고, 그 결과를 새 지역 변수에 담는다
        //    - 이 아래로는 이 변수만 쓴다
        String strippedName = name.strip();
        // 3. 중복 검사: 무기 저장소의 findByName에 정리한 이름을 넘긴다
        //    - 결과가 null이 아니면 같은 이름이 이미 있는 것이므로 예외를 던진다
        if(weaponRepository.findByName(strippedName) != null){
            throw new IllegalArgumentException("무기 이름이 중복됩니다");
        }
        // 4. 저장:
        //    - id 자리에 null을 넣고 새 Weapon을 만든다 (id는 저장소가 붙여 줌)
        Weapon newWeapon = new Weapon(null, strippedName, type);
        //    - 무기 저장소의 save에 넘긴다
        return weaponRepository.save(newWeapon);
        //    - save가 돌려준 객체를 return한다 (id는 그 객체에만 있음)
    }

    public List<Weapon> findAll() {
        // 무기 저장소의 findAll 결과를 그대로 return한다
        return weaponRepository.findAll();
    }

    public void delete(Long weaponId) {
        // 1. 무기 저장소의 findById로 무기를 찾는다
        Weapon findWeapon = weaponRepository.findById(weaponId);
        //    - 결과가 null이면 없는 무기이므로 예외를 던진다
        if(findWeapon == null) {
            throw new IllegalArgumentException("존재하지 않는 무기입니다");
        }
        
        // 2. 아래의 isUsedInAnyMatch(weaponId)를 부른다
        if(isUsedInAnyMatch(weaponId)){
            throw new IllegalArgumentException("기록에 쓰인 무기입니다");
        }
        //    - true가 나오면 기록에 쓰인 무기이므로 예외를 던진다
        
        // 3. 둘 다 통과하면 무기 저장소의 deleteById로 지운다
        weaponRepository.deleteById(weaponId);
    }

    private boolean isUsedInAnyMatch(Long weaponId) {
        // 판 기록 전체를 돌면서, 이 무기를 쓴 기록이 하나라도 있는지 확인한다
        List<GameMatch> games = gameMatchRepository.findAll();
        // 1. 바깥 for문: 판 기록 저장소의 findAll로 받은 판 기록을 하나씩 꺼낸다 (타입 GameMatch)
        for (GameMatch gameMatch : games) {
            // 2. 안쪽 for문: 꺼낸 판 기록의 getWeaponDamages()를 하나씩 꺼낸다 (타입 WeaponDamage)
            for (WeaponDamage weaponDamage : gameMatch.getWeaponDamages()) {
                // 3. 꺼낸 WeaponDamage에서 getWeapon()으로 무기를 꺼내고, 그 무기에서 getId()로 id를 꺼낸다
                Long usedWeaponId = weaponDamage.getWeapon().getId();
                //그 id가 weaponId와 같으면 바로 true를 return한다
                //- id는 Long 객체라는 걸 기억하고 비교 방법을 고른다
                if(usedWeaponId.equals(weaponId)){
                    return true;
                }
            }
        }
        // 4. 두 for문이 다 끝날 때까지 못 찾았으면 false를 return한다
        //    - 이 줄이 어느 중괄호 밖에 있어야 하는지 주의한다
        return false;
    }
}