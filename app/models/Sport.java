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

    public int computeCalories(int weight, int height, int age, int time) {
        int category=0;
        int kcal=0;
        if(weight<=64){
            category=1;
        }
        else if(weight>=65&&weight<=74){
            category=2;
        }
        else if(weight>=75&&weight<=84){
            category=3;
        }
        else if(weight>=85){
            category=4;
        }
        //15 min klettern verbrennt 80 kalorien
        if(this == CLIMBING) {
            switch(category){
                case 1:
                    kcal=80/15;
                    break;
                case 2:
                    kcal=96/15;
                    break;
                case 3:
                    kcal=110/15;
                    break;
                case 4:
                    kcal=125/15;
                    break;
            }
        }
        else if(this == SWIMMING) {
            switch(category){
                case 1:
                    kcal=90/15;
                    break;
                case 2:
                    kcal=107/15;
                    break;
                case 3:
                    kcal=124/15;
                    break;
                case 4:
                    kcal=140/15;
                    break;
            }
        }

        else if(this == TENNIS) {
            switch(category){
                case 1:
                    kcal=134/15;
                    break;
                case 2:
                    kcal=158/15;
                    break;
                case 3:
                    kcal=183/15;
                    break;
                case 4:
                    kcal=206/15;
                    break;
            }
            kcal=kcal*time;
        }
        return kcal;
    }


    public String getDescription() {
        return description;
    }
}
