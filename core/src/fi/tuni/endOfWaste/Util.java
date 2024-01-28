package fi.tuni.endOfWaste;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

/**
 * Util is class of misc code. Here we have method for animation transformation from 2D to 1D
 * array.
 * @author Daniel Bardo & Aleksi Asikainen
 */
public class Util {
    /**
     * Makes 2D array of textureRegions to 1D array.
     */
    public static TextureRegion[] toTextureArray(TextureRegion [][]tr, int cols, int rows ) {
        TextureRegion [] frames
                = new TextureRegion[cols * rows];

        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                frames[index++] = tr[i][j];
            }
        }
        return frames;
    }
}
