package Coordinates;

/**
 * A class to handle plants location
 * contains static methods to give aid in
 * managing map locations
 *
 * @author Feij, Mohammad
 */
public class Coordinates {

    //end of column x location
    public static int[] xs = new int[]{55, 180, 300, 440, 570, 695, 820, 955, 1075};
    //end of row y location
    public static int[] ys = new int[]{125, 235, 345, 445, 565};


    /**
     * Give a good location for plant
     * @param x The location x coordinate
     * @param y The location y coordinate
     * @return The location
     */
    public static int[] getCoordinates(int x, int y){
        int[] arr = new int[2];

        int xp = 0;
        int yp = 0;

        if (x <= 55){
        }
        else if (x <= 180){
            xp = 65;
        }
        else if (x <= 300){
            xp = 190;
        }
        else if (x <= 440){
            xp = 310;
        }
        else if (x <= 570){
            xp = 450;
        }
        else if (x <= 695){
            xp = 580;
        }
        else if (x <= 820){
            xp = 705;
        }
        else if (x <= 955){
            xp = 830;
        }
        else if (x <= 1075){
            xp = 965;
        }
        else if (x <= 1210){
            xp = 1085;
        }

        if (y <= 130){
        }
        else if (y <= 235){
            yp = 140;
        }
        else if (y <= 345){
            yp = 245;
        }
        else if (y <= 470){
            yp = 355;
        }
        else if (y <= 565){
            yp = 480;
        }
        else if (y <= 685){
            yp = 575;
        }

        arr[0] = xp;
        arr[1] = yp;

        return arr;
    }

    /**
     * A method to get row number of a certain y location
     * @param locY y location
     * @return row number of the y location
     */
    public static int getRow(int locY){
        locY -= 60;
       for(int i = 0 ; i < 5 ; i++)
           if(locY < ys[i])
               return i;
       return -1;
    }

    /**
     * A method to check if two y locations are in the same row
     * @param locY1 first y location
     * @param locY2 second y location
     * @return true if locations are at the same row
     */
    public static boolean checkRowEquality(int locY1, int locY2){
        return getRow(locY1) == getRow(locY2);
    }

}
