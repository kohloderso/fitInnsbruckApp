package models;

/**
 * Created by Christina on 18.01.2015.
 */
public enum Sport {
    CLIMBING("Fitness/Klettern", "KLettergarten", "Kletterhalle"),
    SWIMMING("Schwimmen","Freibad", "Hallenbad"),
    TENNIS("Tennis/Squash", "Tennisplatz", "Tennishalle");

    private String description;
    private String roofedPlace;
    private String outsidePlace;

    private Sport(String description, String outsidePlace, String roofedPlace) {
        this.description = description;
        this.roofedPlace = roofedPlace;
        this.outsidePlace = outsidePlace;
    }

    public int computeCalories(int age, int time) {
        if(this == CLIMBING) {

            //TODO
        } else if(this == SWIMMING) {

        } else if(this == TENNIS) {

        }
        return 0;
    }


}
