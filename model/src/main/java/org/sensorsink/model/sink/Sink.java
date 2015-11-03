package org.sensorsink.model.sink;

import java.time.Instant;
import org.sensorsink.model.points.Point;

public interface Sink
{
    boolean isSupporting(Class<?> type);

    void place(Point point, Instant timestamp);
}
