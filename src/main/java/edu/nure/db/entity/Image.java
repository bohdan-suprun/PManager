package edu.nure.db.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by bod on 17.09.15.
 */
public class Image implements Transmittable{
    public static final int ID_NOT_SET = -1;
    private int id = ID_NOT_SET;
    private String hash;
    private byte[] image;
    private int album;
    private Date createdIn;

    public Image(String hash, int id, byte[] image, int album, Date createdIn) {
        setHash(hash);
        setId(id);
        setImage(image);
        setAlbum(album);
        setCreatedIn(createdIn);
    }

    public static String[] getFields() {
        return new String[]{"Hash", "Album", "CreatedIn", "Image"};
    }

    public String getCreatedIn() {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(createdIn);
    }

    public void setCreatedIn(Date createdIn) {
        this.createdIn = createdIn;
    }

    public int getAlbum() {
        return album;
    }

    public void setAlbum(int album) {
        this.album = album;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toXML() {
        return "<image id=\""+id+"\" hash=\""+hash+"\" album=\""
                + album +"\" createdIn=\""+getCreatedIn()+"\"/>";
    }

    @Override
    public String toQuery() {
        return "id=" + id +
                "&hash=" + hash +
                "&albumId=" + album +
                "&createdIn=" + getCreatedIn();
    }

}
