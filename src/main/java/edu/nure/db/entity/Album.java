package edu.nure.db.entity;

/**
 * Created by bod on 07.10.15.
 */
public class Album implements Transmittable{
    public static final int ID_NOT_SET = -1;
    private String name;
    private int id;
    private int userId;

    public Album(String name, int id, int userId) {
        this.name = name.replace("'", "\"");
        this.id = id;
        this.userId = userId;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public String toXML() {
        return "<album name=\""+name.replace("\"", "'")+"\" id = \""+id+"\" userId=\""+userId+"\"/>";
    }

    @Override
    public String toQuery() {
        return "name="+name.replace("\"", "'")+"&id="+id+"&userId="+userId;
    }

}
