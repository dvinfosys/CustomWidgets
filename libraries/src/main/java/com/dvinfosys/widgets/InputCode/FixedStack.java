package com.dvinfosys.widgets.InputCode;

import java.util.Stack;

public class FixedStack<T> extends Stack<T> {
    int maxSize=0;

    @Override
    public T push(T item) {
        if (maxSize > size()) {
            return super.push(item);
        }
        return item;
    }

    public int getMaxSize() {
        return maxSize;
    }

    public void setMaxSize(int maxSize) {
        this.maxSize = maxSize;
    }
}
