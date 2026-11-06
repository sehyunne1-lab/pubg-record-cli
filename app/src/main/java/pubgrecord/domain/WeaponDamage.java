package pubgrecord.domain;

public class WeaponDamage {

    private final Weapon weapon;
    private final int damage;

    public WeaponDamage(Weapon weapon, int damage){
        this.weapon = weapon;
        this.damage = damage;
    }

    public Weapon getWeapon(){
        return weapon;
    }

    public int getDamage(){
        return damage;
    }
}
