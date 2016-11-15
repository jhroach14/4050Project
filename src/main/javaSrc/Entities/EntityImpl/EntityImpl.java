package main.javaSrc.Entities.EntityImpl;

import main.javaSrc.Entities.Entity;

/**
 * Created by User on 10/31/2016.
 */
public abstract class EntityImpl  implements Entity{
    private int id;
    private boolean persistent=false;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public void setPersistent(boolean persistent){
        this.persistent = persistent;
    }

    public boolean isPersistent() {
        return persistent;
    }
}
