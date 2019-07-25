package Gra.GUI;

import java.util.Objects;

public class EventTrigger {

    int tura;
    TriggerAnimation triggerAnimation;

    public EventTrigger(int tura, TriggerAnimation triggerAnimation) {
        this.tura = tura;
        this.triggerAnimation = triggerAnimation;
    }

    public int getTura() {
        return tura;
    }

    public TriggerAnimation getTriggerAnimation() {
        return triggerAnimation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventTrigger that = (EventTrigger) o;
        return tura == that.tura &&
                Objects.equals(triggerAnimation, that.triggerAnimation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tura, triggerAnimation);
    }
}
