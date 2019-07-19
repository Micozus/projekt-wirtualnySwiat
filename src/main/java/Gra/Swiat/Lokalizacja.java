package Gra.Swiat;

import java.util.Objects;

public class Lokalizacja {
    private int xValue;
    private int Yvalue;

    public Lokalizacja(int xValue, int yvalue) {
        this.xValue = xValue;
        Yvalue = yvalue;
    }

    public int getxValue() {
        return xValue;
    }

    public int getYvalue() {
        return Yvalue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lokalizacja that = (Lokalizacja) o;
        return xValue == that.xValue &&
                Yvalue == that.Yvalue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(xValue, Yvalue);
    }
}
