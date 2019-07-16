package com.dvinfosys.widgets.NumberCounter;

public class InvalidLimitsException extends RuntimeException {
    InvalidLimitsException() {
        super("You should use valid min and max values, please verify them!");
    }
}
