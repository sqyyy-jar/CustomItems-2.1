package dev.sqyyy.customitems.ii.exceptions;

public class NotStackableError extends Exception {
    public NotStackableError(String name) {
        super("The itemstack is not stackable!!!");
    }
}
