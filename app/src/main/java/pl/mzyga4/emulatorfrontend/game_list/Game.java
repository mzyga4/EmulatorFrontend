package pl.mzyga4.emulatorfrontend.game_list;


import androidx.annotation.NonNull;

public class Game {
    public String id;
    public String content;
    public String details;

    public Game() { }

    public Game(String id, String content, String details) {
        this.id = id;
        this.content = content;
        this.details = details;
    }

    @NonNull
    @Override
    public String toString() {
        return content;
    }

    public String getId() {
        return id;
    }

    public String getContent() {
        return content;
    }

    public String getDetails() {
        return details;
    }
}