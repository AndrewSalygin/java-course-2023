package edu.hw4;

public enum ValidationError {
    AGE_ERROR("age"),
    HEIGHT_ERROR("height"),
    NAME_ERROR("name"),
    NULL_OBJECT("null object"),
    WEIGHT_ERROR("weight");

    String field;

    ValidationError(String s) {
        field = s;
    }
}
