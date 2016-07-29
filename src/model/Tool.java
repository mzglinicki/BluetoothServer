package model;

/**
 * Created by mzglinicki.96 on 29.07.2016.
 */
public class Tool {

    private int toolId;
    private int toolXCoordinate;
    private int toolYCoordinate;
    private int toolZCoordinate;

    public Tool(final int toolId, final int x, int y, int z) {
        this.toolId = toolId;
        this.toolXCoordinate = x;
        this.toolYCoordinate = y;
        this.toolZCoordinate = z;
    }

    public int getToolId() {
        return toolId;
    }

    public int getToolXCoordinate() {
        return toolXCoordinate;
    }

    public int getToolYCoordinate() {
        return toolYCoordinate;
    }

    public int getToolZCoordinate() {
        return toolZCoordinate;
    }
}