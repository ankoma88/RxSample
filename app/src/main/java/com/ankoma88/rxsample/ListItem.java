package com.ankoma88.rxsample;

import java.io.Serializable;

public class ListItem implements Serializable {
    private String line1;
    private String line2;

    public ListItem(String line1, String line2) {
        this.line1 = line1;
        this.line2 = line2;
    }

    public String getLine1() {
        return line1;
    }

    public String getLine2() {
        return line2;
    }

    @Override
    public String toString() {
        return "ListItem{" +
                "line1='" + line1 + '\'' +
                ", line2='" + line2 + '\'' +
                '}';
    }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    ListItem listItem = (ListItem) o;

    if (!line1.equals(listItem.line1)) return false;
    return line2.equals(listItem.line2);

  }

  @Override
  public int hashCode() {
    int result = line1.hashCode();
    result = 31 * result + line2.hashCode();
    return result;
  }
}
