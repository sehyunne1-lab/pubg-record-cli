package pubgrecord.repository;

import java.util.ArrayList;
import java.util.List;
import pubgrecord.domain.Weapon;

public class MemoryWeaponRepository implements WeaponRepository {

    private final List<Weapon> store = new ArrayList<>();
    private long sequence = 0;

    @Override
    public Weapon save(Weapon weapon) {
        // 1. 번호표(sequence)를 1 올린다
        sequence++;
        // 2. 올린 번호를 id로 넣은 새 Weapon을 만든다 (이름, 종류는 받은 weapon에서 꺼낸다)
        Weapon result = new Weapon(sequence,weapon.getName(),weapon.getType());
        // 3. 새로 만든 Weapon을 store에 넣는다
        store.add(result);
        // 4. 새로 만든 Weapon을 돌려준다
        return result;
    }

    @Override
    public Weapon findById(Long id) {
        // 1. store를 처음부터 하나씩 돈다
        for (Weapon weapon : store) {
            // 2. 무기의 id가 받은 id와 같으면 그 무기를 바로 돌려준다
            if(weapon.getId().equals(id)){
                return weapon;
            }
        }
        return null;
    }

    @Override
    public Weapon findByName(String name) {
        // findById와 같은 흐름. 비교하는 게 id가 아니라 이름이다
        for (Weapon weapon : store) {
            // 2. 무기의 이름이 받은 이름과 같으면 그 무기를 바로 돌려준다
            if(weapon.getName().equals(name)){
                return weapon;
            }
        }
        return null;
    }

    @Override
    public List<Weapon> findAll() {
        // store를 그대로 주지 말고, store 내용을 담은 새 ArrayList를 만들어 돌려준다
        return new ArrayList<Weapon>(store);
    }

    @Override
    public void deleteById(Long id) {
        // 1. 인덱스(i)로 store를 돈다 (향상된 for문 X)
        for (int i = 0; i < store.size(); i++) {
            // 2. i번째 무기의 id가 받은 id와 같으면 i번째를 지우고 바로 끝낸다
            if(store.get(i).getId().equals(id)){
                store.remove(i);
            }
        }
    }
}