
import java.applet.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by frien on 2/10/2016.
 */
public class display extends JApplet implements Runnable, MouseListener, MouseMotionListener {
    Color lightblue = new Color(100,255,255); //edges of aram for ice color
    Thread t; //main thread
    int timeStep = 30; //sleep
    public static int [][] board = new int[50][70]; //creates 2D array for collision detection
    public static int mouseXclick, mouseYclick; //where the mousex and mouse y were when clicked
    int mouseX, mouseY; //where the mousex and mouse y are
    Image offscreen; //makes double buffered image
    public static Graphics bg; //makes double buffering graphics
    player player1; //creates the object that the player physically interacts with and does actions
    ArrayList<minion> blueMinions; //the blue side (top) minions
    ArrayList<minion> redMinions; //the red side(bot) minions
    garen champ = new garen(); //creates an instance of garen with variables
    public static boolean attackAnimate = false; //boolean for switching on and off attack animation
    public static boolean startAttack = false; //attack command (clicking on enemy) was registered
    int attackSpeedCount = 0;  //lag time in between autos
    public static int mapy = -500; //y position of the canvas
    ArrayList<turret> blueTowers = new ArrayList<>();
    ArrayList<turret> redTowers = new ArrayList<>();

    public void init(){
        resize(1500, 900); // make screen 1500x900
        offscreen = createImage(1500,900); //make double buffering image offscreen to match
        bg = offscreen.getGraphics(); //load graphics of the double buffer image
        player1 = new player(champ); //player inherits selected champ's stats
        addMouseListener(this);
        addMouseMotionListener(this);
        mouseXclick = 500;
        mouseYclick = 800;
        blueMinions = new ArrayList<minion>();
        redMinions = new ArrayList<minion>();
        for(int i = 0; i < 6; i++){
            blueMinions.add(new minion(585 + i * 60, 15)); //initializes a line of minions near top
        }
        for(int i = 0; i < 2; i++) {
            blueTowers.add(new turret(570, (210 + 390 * i), i + 1));
        }

        for(int i = 0; i < 6; i++){
            redMinions.add(new minion(585 + i * 60, 2100)); //initializes a line of minions near bot
        }
        for(int i = 0; i < 2; i++){
            redTowers.add(new turret(570, 1890 - (390 * i), i + 1));
        }
        t = new Thread(this);
        t.start();
    }
    public void run() {
        try{
            while(true){
                repaint();
                t.sleep(timeStep);

                    for(int i = 0; i < board.length; i++){
                        for(int j = 0; j < board[0].length; j++){
                            for(minion m : blueMinions) { // check each blue minion vs the board
                                if(board[i][j] == 1){
                                    if(m.x + 5 < i * 30 || m.x + 5 > (i+1) * 30 && m.y + 5 + mapy < j * 30 + mapy || m.y + 5 + mapy > (j+1) * 30 + mapy)
                                        board[i][j] = 0;
//                                    if(m.y + 5 + mapy < j * 30 + mapy || m.y + 5 + mapy > (j+1) * 30 + mapy)
//                                        board[i][j] = 0;
                                }
                                if (m.x + 5 > (i * 30) && m.x + 5 < (i + 1) * 30 && m.y + mapy > (j * 30) + mapy && m.y + mapy < ((j + 1) * 30) + mapy) {
                                    board[i][j] = 1;
                                    break;
                                }
                            }
                            for(minion m : redMinions) { //repeat for redminions
//                                if(board[i][j] == 1){
//                                   if(m.x + 5 < i * 30 || m.x + 5 > (i+1) * 30 && m.y + 5 + mapy < j * 30 + mapy || m.y + 5 + mapy > (j+1) * 30 + mapy)
//                                    board[i][j] = 0;
//                                    if(m.y + 5 + mapy < j * 30 + mapy || m.y + 5 + mapy > (j+1) * 30 + mapy)
//                                        board[i][j] = 0;
//                                }
                                if (m.x + 5 > (i * 30) && m.x + 5 < (i + 1) * 30 && m.y + mapy > (j * 30) + mapy && m.y + mapy < ((j + 1) * 30) + mapy) {
                                    board[i][j] = 1;
                                    break;
                                }
                            }
                            for(turret t : blueTowers){
                                if(i * 30 >= t.x && i * 30 < t.x + t.width && j*30 >= t.y && j*30 < t.y + t.height)
                                    board[i][j] = 2;
                            }
                            for(turret t : redTowers){
                                if(i * 30 >= t.x && i * 30 < t.x + t.width && j*30 >= t.y && j*30 < t.y + t.height)
                                    board[i][j] = 2;
                            }
                        }
                    }

                if(player1.moving == true)
                    player1.move(mouseXclick, mouseYclick); //moves player
                if(player1.attacking == true)
                    player1.attack(mouseX, mouseY, bg); //makes player attack or move in range to attack

//                        for(minion m : blueMinions){
//                            for(int i = 0; i < board.length; i++) {
//                                for (int j = 0; j < board[0].length; j++) {
//                                    if (board[i][j] == 1) {
//                                        if (j < 69 && board[i][j + 1] == 0)
//                                            m.move(1); //moves all blue minions
//                                    }
//                                }
//                            }
//                        }
//                        for(minion m : redMinions){
//                            for(int i = 0; i < board.length; i++) {
//                                for (int j = 0; j < board[0].length; j++) {
//                                    if (board[i][j] == 1) {
//                                        if (j > 0 && board[i][j - 1] == 0)
//                                            m.move(-1); //moves all red minions
//                                    }
//                                }
//                            }
//
//                        }
                for(minion m : blueMinions){
                    m.move(1);
                }
                for(minion m : redMinions){
                    m.move(-1);
                }

                if(startAttack == true) { //if a player is in range to attack
                    attackSpeedCount++; //goes from 0 and ticks up each timestep
                    if(attackSpeedCount > 50) { //once it hits 50, the auto animation begins
                        attackAnimate = true; //tells paint to paint the animation
                        repaint();
                        if(attackSpeedCount > 70) { //after 20 timesteps, it stops the animation
                            attackSpeedCount = 0; //reset timer between autos, can be recoded later for attack speed
                            attackAnimate = false; //makes paint not paint it anymore
                        }

                    }
                }
            }
        } catch(InterruptedException e){}
    }

    public void paint(Graphics g){
        bg.clearRect(0,0,2000,2100); //clear the double buffer image
        drawMap(0, mapy, g); //calls method to draw the map
        g.drawImage(offscreen,0,0,this); //places double buffered image onto the screen
    }

    public void drawMap(int x, int y, Graphics g) {
        Graphics2D g2d = (Graphics2D) bg; //creates new graphics2d for better drawing
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);//rounds edges of shapes
        bg.setColor(lightblue); //draws edges of aram
        bg.fillRect(x, y, 1500, 2100);

        bg.setColor(Color.lightGray); //draws lane
        bg.fillRect(x + 90, y, 1320, 2100);
        bg.setColor(Color.red); // draws a reference point
        bg.fillOval(750, y + 750, 40, 40);
        bg.fillRect(player1.x - player1.w / 2, player1.y - player1.h / 2, player1.w, player1.h); // draw the player avatar
        bg.setColor(Color.blue);
        bg.fillOval(mouseXclick, mouseYclick, 10,10); //draws the point of destination
        bg.fillOval(player1.x, player1.y, 10, 10); //draws the coordinates of the player
        bg.setColor(Color.green);
        bg.fillOval(player1.orx, player1.ory, 10, 10); //draws player's original position
        bg.setColor(Color.blue); //draws reference numbers
        for(int i = 0; i < 70; i++){
            bg.drawString("" + ((30 * i)), 50, y + (30 * i));
        }
        for(int i = 0; i < blueMinions.size(); i++){ //draws blue minions
            bg.setColor(Color.blue);
            bg.fillOval(blueMinions.get(i).x - 15, mapy + blueMinions.get(i).y - 15, 30, 30);
            bg.setColor(Color.black);
            bg.drawString("" + blueMinions.get(i).y, blueMinions.get(i).x, mapy + blueMinions.get(i).y);
        }

        for(turret t : blueTowers){
            bg.setColor(Color.blue);
            bg.fillRect(t.x, mapy + t.y, t.width, t.height);
        }

        for(int i = 0; i < redMinions.size(); i++){ //draws red minions
            bg.setColor(Color.red);
            bg.fillOval(redMinions.get(i).x - 15, mapy + redMinions.get(i).y - 30, 30, 30);
        }
        for(turret t : redTowers){
            bg.fillRect(t.x, mapy + t.y, t.width, t.height);
        }
        for(int i = 0; i < board.length; i++){ //draws 2D array for reference, can be commented later
            for(int j = 0; j < board[0].length; j++){

                bg.setColor(Color.darkGray);
                bg.drawRect(i * 30, (j * 30) + mapy, 30, 30);
                bg.drawString("" + board[i][j], (i*30) + 5, (j*30) + 20 + mapy);

            }
        }
        if(attackAnimate == true){ //draws the attack animation as a line from target to player, can make projectiles later for ranged champs
            bg.drawLine(mouseX, mouseY, player1.x, player1.y);
        }
    }

    public void update(Graphics g){
        paint(g);
    } //updates the double buffer image onto the canvas




    public void mouseClicked(MouseEvent e) {
        if(e.getButton() == MouseEvent.BUTTON3) { //checks for right click only
            player1.attacking = false; //turns off attacking, only use left click for attacking?
            player1.moving = true; //turns move on
            mouseXclick = e.getX(); //gets mouse x for destination variable
            mouseYclick = e.getY(); //gets mouse y for destination variable
            player1.ormapy = mapy; //sets "original" canvas y position to current for distance calc in move
            //player1.orx = player1.x;
            player1.ory = player1.y; //sets player's "original" y position to current (should  not change)
        }
        if(e.getButton() == MouseEvent.BUTTON1) { //if left click
            mouseX = e.getX(); //get mouse x for attack variable
            mouseY = e.getY(); //get mousey for attack variable
            for(minion m : blueMinions){ //if any minions were clicked, turn attacking on
                if(mouseX > m.x - m.w/2 && mouseX < m.x + m.w/2 && mouseY > m.y - m.w/2 && mouseY < m.y + m.w/2){
                    player1.attacking = true;
                    player1.moving = false;
                }
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }



}
