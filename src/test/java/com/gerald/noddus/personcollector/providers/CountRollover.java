package com.gerald.noddus.personcollector.providers;

public class CountRollover implements RolloverLabel {
    private long count = 0;
    private String prefix;

    public void setPrefix(String prefix) {
        this.prefix = prefix + "-";
    }

    @Override
    public String prefix() {
        return prefix;
    }

    @Override
    public String suffix() {
        return "-" + Long.toString(count);
    }

    public void increase(){
        count++;
    }
}
