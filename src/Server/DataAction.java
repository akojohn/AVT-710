package Server;

import java.util.ArrayList;
import java.util.UUID;

public class DataAction { //хранит инф-ю, которую надо передать
    private String command;
    private UUID uuid;
    private ArrayList<UUID> uuids;
    private UUID asker, sender;

    private String guppyInterval, goldenInterval;
    private int guppyProbability, goldenProbability;
    private String guppyLifeTime, goldenLifeTime;

    public DataAction(String command){
        this.command = command;
    }

    public String getCommand(){
        return command;
    }

    public void setCommand(String command){
        this.command = command;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid){
        this.uuid = uuid;
    }

    public ArrayList<UUID> getUuids() {
        return uuids;
    }

    public void setUuids(ArrayList<UUID> uuids) {
        this.uuids = uuids;
    }

    public UUID getAsker() {
        return asker;
    }

    public void setAsker(UUID asker) {
        this.asker = asker;
    }

    public UUID getSender() {
        return sender;
    }

    public void setSender(UUID sender) {
        this.sender = sender;
    }

    public String getGuppyInterval() {
        return guppyInterval;
    }

    public void setGuppyInterval(String guppyInterval) {
        this.guppyInterval = guppyInterval;
    }

    public String getGoldenInterval() {
        return goldenInterval;
    }

    public void setGoldenInterval(String goldenInterval) {
        this.goldenInterval = goldenInterval;
    }

    public int getGuppyProbability() {
        return guppyProbability;
    }

    public void setGuppyProbability(int guppyProbability) {
        this.guppyProbability = guppyProbability;
    }

    public int getGoldenProbability() {
        return goldenProbability;
    }

    public void setGoldenProbability(int goldenProbability) {
        this.goldenProbability = goldenProbability;
    }

    public String getGuppyLifeTime() {
        return guppyLifeTime;
    }

    public void setGuppyLifeTime(String guppyLifeTime) {
        this.guppyLifeTime = guppyLifeTime;
    }

    public String getGoldenLifeTime() {
        return goldenLifeTime;
    }

    public void setGoldenLifeTime(String goldenLifeTime) {
        this.goldenLifeTime = goldenLifeTime;
    }
}
