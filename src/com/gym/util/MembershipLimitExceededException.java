package com.gym.util;

public class MembershipLimitExceededException extends Exception {
    public String toString() {
        return "Membership limit exceeded";
    }
}
