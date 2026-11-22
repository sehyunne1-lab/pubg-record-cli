package pubgrecord.repository;

import java.util.ArrayList;
import java.util.List;
import pubgrecord.domain.GameMatch;

public class MemoryGameMatchRepository implements GameMatchRepository {

    private final List<GameMatch> store = new ArrayList<>();
    private long sequence = 0;

    @Override
    public GameMatch save(GameMatch gameMatch) {
        // 1. 번호표를 1 올린다
        sequence++;
        // 2. 올린 번호를 id로 넣은 새 GameMatch를 만든다
        //    - 생성자에는 id, 날짜, 무기 딜량 목록 순서로 넣는다
        //    - 날짜와 무기 딜량 목록은 받은 gameMatch에서 getter로 꺼낸다
        GameMatch newGameMatch = new GameMatch(sequence,gameMatch.getDate(),gameMatch.getWeaponDamages());
        // 3. 새 GameMatch를 store에 넣는다
        // 4. 새 GameMatch를 돌려준다
        store.add(newGameMatch);
        return newGameMatch;
    }

    @Override
    public GameMatch findById(Long id) {
        // 1. store에 든 판 기록을 처음부터 하나씩 꺼낸다
        for (GameMatch gameMatch : store) {
            // 2. 꺼낸 판 기록의 id가 받은 id와 같으면 그 판 기록을 바로 돌려준다
            if(gameMatch.getId().equals(id)){
                return gameMatch;
            }
            //    - id는 Long 객체라는 걸 기억하고 비교 방법을 고른다
            // 3. 끝까지 다 봐도 없으면 null을 돌려준다   
        }
        return null;
    }

    @Override
    public List<GameMatch> findAll() {
        // store의 내용을 복사한 새 리스트를 돌려준다
        return new ArrayList<>(store);
    }

    @Override
    public void deleteById(Long id) {
        // 1. 인덱스 i를 0부터 store의 크기 직전까지 올리며 돈다
        for (int i = 0; i < store.size(); i++) {
            if(store.get(i).getId().equals(id)){
                store.remove(i);
                return;
            }
        }
        // 2. i번째 판 기록의 id가 받은 id와 같으면
        //    i번째를 지우고 메서드를 끝낸다
    }
}