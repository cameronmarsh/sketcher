package cmarsh.npr.model;

import java.awt.image.BufferedImage;
import java.util.Arrays;

public class PixMap {
    private int imgWidth;
    private int imgHeight;
    int[][] grid;

    public PixMap(BufferedImage image) {
        imgWidth = image.getWidth();
        imgHeight = image.getHeight();
        grid = new int[imgWidth][imgHeight];

        for(int i = 0; i < imgWidth; i++){
            for(int j = 0; j < imgHeight; j++){
                grid[i][j] = image.getRGB(i,j);
            }
        }
    }
}
