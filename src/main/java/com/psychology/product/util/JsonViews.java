package com.psychology.product.util;

public final class JsonViews {
    public interface UUID{}

    public interface FirstName{}
    public interface LastName{}
    public interface Email{}
    public interface Phone{}
    public interface UserView extends UUID,FirstName,LastName,Email,Phone{

    }
}
