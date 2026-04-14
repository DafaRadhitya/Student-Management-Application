/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend;

/**
 *
 * @author Rezan
 */
public class ComboItem {
    private final int id;
    private final String display;

    public ComboItem(int id, String display) {
        this.id = id;
        this.display = display;
    }

    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return display;
    }
}
