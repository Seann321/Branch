package controls;

import states.Handler;

import java.awt.*;
import java.awt.event.*;

public class MouseManager implements MouseListener, MouseMotionListener, MouseWheelListener{

    private boolean leftPressed, rightPressed, middlePressed, leftClicked, rightClicked;
    private int mouseX, mouseY;
    private Handler handler;

    public MouseManager(Handler handler) {
        this.handler = handler;
    }

    public boolean isLeftPressed() {
        return leftPressed;
    }

    public void setLeftPressed(boolean leftPressed) {
        this.leftPressed = leftPressed;
    }

    public boolean isRightPressed() {
        return rightPressed;
    }

    public void setRightPressed(boolean rightPressed) {
        this.rightPressed = rightPressed;
    }

    public boolean isMiddlePressed() {
        return middlePressed;
    }

    public void setMiddlePressed(boolean middlePressed) {
        this.middlePressed = middlePressed;
    }

    public int getMouseX() {
        return mouseX;
    }

    public void setMouseX(int mouseX) {
        this.mouseX = mouseX;
    }

    public int getMouseY() {
        return mouseY;
    }

    public void setMouseY(int mouseY) {
        this.mouseY = mouseY;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftClicked = true;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            middlePressed = true;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = true;
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {

        if (e.getButton() == MouseEvent.BUTTON1) {
            leftPressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON2) {
            middlePressed = false;
        }
        if (e.getButton() == MouseEvent.BUTTON3) {
            rightPressed = false;
        }

        handler.getCurrentGUI().onMouseRelease(e);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mouseX = e.getX();
        mouseY = e.getY();

        handler.getCurrentGUI().onMouseMove(e);
    }

    public boolean isLeftClicked() {
        if (leftClicked) {
            leftClicked = false;
            return true;
        } else {
            return false;
        }
    }

    public void setLeftClicked(boolean leftClicked) {
        this.leftClicked = leftClicked;
    }

    public boolean isRightClicked() {
        return rightClicked;
    }

    public void setRightClicked(boolean rightClicked) {
        this.rightClicked = rightClicked;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation() > 0){
            mouseDown = true;
        }if(e.getWheelRotation() < 0){
            mouseUp = true;
        }
    }

    private boolean mouseDown = false;
    private boolean mouseUp = false;

    public boolean isWheelDown(){
        if(mouseDown){
            mouseDown = false;
        return true;
        }
        return false;
    }

    public boolean isWheelUp(){
        if(mouseUp){
            mouseUp = false;
        return true;
        }
        return false;
    }
}