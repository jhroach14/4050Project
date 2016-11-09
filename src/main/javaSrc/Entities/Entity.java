package main.javaSrc.Entities;

public interface Entity {
    int getId();

    void setId(int id);

    void setPersistent(boolean persistent);

    boolean isPersistent();
}


