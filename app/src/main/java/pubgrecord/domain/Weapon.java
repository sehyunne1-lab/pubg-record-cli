package pubgrecord.domain;

public class Weapon {

    private final Long id;
    private final String name;
    private final WeaponType type;

    public Weapon(Long id, String name, WeaponType type){
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Long getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public WeaponType getType(){
        return type;
    }
}
