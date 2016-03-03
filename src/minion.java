/**
 * Created by frien on 2/11/2016.
 */
public class minion {
    int x, y, w, h, v, ad, arm, mr, ms;
    public minion(int x, int y){
        this.x = x;
        this.y = y;
        w = 30;
        h = 30;
        v = 1;
    }
    public void move(int dir) {
        if(y >= 0 && y <= 2100 && display.board[x / 30][(y / 30) + dir] != 1){
            y += (dir * v);
        }
    }
}
