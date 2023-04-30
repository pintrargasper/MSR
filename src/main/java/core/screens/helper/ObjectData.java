package core.screens.helper;

public class ObjectData {

    private int height;
    private int width;
    private float posX;
    private float posY;

    public ObjectData(int height, int width, float posX, float posY) {
        this.height = height;
        this.width = width;
        this.posX = posX;
        this.posY = posY;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public float getPosX() {
        return posX;
    }

    public float getPosY() {
        return posY;
    }
}