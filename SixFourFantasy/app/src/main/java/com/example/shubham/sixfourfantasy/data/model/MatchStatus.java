package com.example.shubham.sixfourfantasy.data.model;

public enum MatchStatus {
    LIVE, UPCOMING, COMPLETED;

    @Override
    public String toString() {
        return name();
    }
}
