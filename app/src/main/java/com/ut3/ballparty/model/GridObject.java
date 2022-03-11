package com.ut3.ballparty.model;

public class GridObject {

    protected int color;
    protected Shape shape;

    public GridObject(){}

    public GridObject(int color, Shape shape){
        this.color = color;
        this.shape = shape;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }
}
