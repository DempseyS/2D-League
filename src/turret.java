/**
 * Created by frien on 2/11/2016.
 */
public class turret {
    int x, y, hp, range, damage, width, height;

    public turret(int x, int y, int tier){
        this.x = x;
        this.y = y;
        hp = tier * 500;
        damage = tier * 50;
        range = tier * 150;
        width = 330;
        height = 30;
    }
}
