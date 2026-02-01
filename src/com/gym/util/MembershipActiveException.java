package com.gym.util;

public class MembershipActiveException extends Exception {
    public String toString() {
        return "Member has active membership";
    }
}
