import java.awt.*;

/**
 * Created by frien on 2/12/2016.
 */
public class player {
    int x, y, w, h, orx, ory, range, ormapy;
    double theta, v;
    boolean attacking, moving;
    public player(champion c){
        x = c.x;
        orx = c.x;
        y = 450;
        ory = 450;
        w = c.w;
        h = c.h;
        v = c.v;
        ormapy = -500;
        range = c.range;
        attacking = false;
        moving = false;
    }

    public void move(int mx, int my) {
        //my = display.mapy + Math.abs(display.mapy - y);
        for(int i = 0; i < display.board.length; i++){
            for(int j = 0; j < display.board[0].length; j++){
                if(x >= i * 30 && x <= (i+1) * 30 && y >= (j * 30) + display.mapy && y <= ((j+1)*30) + display.mapy){
                    if(display.board[i][j] == 2 || display.board[i][j] == 1){
                        return;
                    }
                }
            }
        }

        if(mx > 1370 || mx < 130) // don't move past the edges of lane
            return;
        if(mx - x < 5 && mx - x >= 0 && my - y < 5 && my - y >=0) // if we have reached our destination, stop
            return;
        if(mx - x > -5 && mx - x <=0 && my - y > -5 && my - y <= 0)
            return;
        if(mx - x < 5 && mx - x >= 0 && my - y > - 5 && my - y <=0)
            return;
        if(mx - x > -5 && mx - x <= 0 && my - y < 5 && my - y >= 0)
            return;

        if(x > 1400 - w / 2)
            x = 1390 - w/2;
        if(x < 100 + w/2)
            x = 110 + w/2;
        double distance = (Math.sqrt( ((mx - x) * (mx - x)) + ((my - y) * (my - y)) ));
        int distancey = Math.abs(ory - my);

        x += ((mx - x) / distance) * 10;
        if(Math.abs(display.mapy - ormapy) > distancey - 10 && Math.abs(display.mapy - ormapy) < distancey + 10){
            //ormapy = display.mapy;
            return;
        }
        if((display.mapy != -1199) && (display.mapy != 1)) { //move canvas, if canvas not on edge
            display.mapy -= ((my - y) / distance) * 5;
        }
        if (display.mapy >= 0) { // top edge of canvas stay in screen
            display.mapy = 1;
        }
        if(display.mapy <= -1200) { // bot edge of canvas stay in screen
            display.mapy = -1199;
        }
        if(display.mapy == -1199 || display.mapy == 1){ //if the edge of the canvas reached
            y += ((my - y) / distance * 10); //can move around normal
            if(display.mapy == -1199 && y < 550){ //if going back to center of canvas
                display.mapy -= ((my - y) / distance) * 5; //reengage normal canvas movement
            }
            if(display.mapy == 1 && y > 550){ //top side same thing
                display.mapy -= ((my - y) / distance) * 5;
            }
            if(y < 40) //stay inside
                y = 41;
            if(y > 860)
                y = 859;
        }
//        if(mx - x < 2 && my - y < 2){
//            return;
//        }
//        theta = Math.atan2( (my - y), (mx - x));
//        x += (v*Math.cos(theta));
//        y += (v*Math.sin(theta));
    }
    public void attack(int ax, int ay, Graphics g){

        if(Math.sqrt( ((ax - x) * (ax - x)) + ((ay - y) * (ay - y))) > range){
            move(ax, ay);
            //display.mouseXclick = ax;
            //display.mouseYclick = ay;
        }
        if(Math.sqrt( ((ax - x) * (ax - x)) + ((ay - y) * (ay - y))) <= range){
            display.startAttack = true;
        }
    }
}
