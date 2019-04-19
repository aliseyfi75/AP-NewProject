package ui;

class Player {
    private String name;
    private boolean hasResume;

    Player(String name) {
        this.name = name;
        /*long level = (long) object.get("Level");
        hasResume = (level != 0);*/
    }

/*
    void SetInitialLevel(JSONObject object){
        object.put("Level", 0);
    }
*/

    String getName() {
        return name;
    }

    boolean hasResume() {
        return hasResume;
    }
}