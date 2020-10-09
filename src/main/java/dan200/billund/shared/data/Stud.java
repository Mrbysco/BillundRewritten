/**
 * This file is part of Billund - http://www.computercraft.info/billund
 * Copyright Daniel Ratcliffe, 2013. Do not distribute without permission.
 * Send enquiries to dratcliffe@gmail.com
 */

package dan200.billund.shared.data;

public class Stud {

    public boolean illuminated;
    public boolean transparent;
    public boolean smooth;
    public int color;
    public int xOrigin;
    public int yOrigin;
    public int zOrigin;
    public int brickWidth;
    public int brickHeight;
    public int brickDepth;

    public boolean actuallyExists = true;

    public Stud() {
    }

    public Stud(boolean illuminated, boolean transparent, boolean smooth, int color, int xOrigin, int yOrigin, int zOrigin, int width, int height, int depth) {
        this.illuminated = illuminated;
        this.transparent = transparent;
        this.smooth = smooth;
        this.color = color;
        this.xOrigin = xOrigin;
        this.yOrigin = yOrigin;
        this.zOrigin = zOrigin;
        brickWidth = width;
        brickHeight = height;
        brickDepth = depth;
    }

    public Stud(Brick brick, int xLocal, int yLocal, int zLocal) {
        this(brick.illuminated, brick.transparent, brick.smooth, brick.color, brick.xOrigin, brick.yOrigin, brick.zOrigin, brick.width, brick.height, brick.depth);
    }
}
