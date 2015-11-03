package org.sensorsink.model.scheduling;

import org.apache.zest.api.association.Association;
import org.apache.zest.api.common.Optional;
import org.apache.zest.api.entity.Identity;
import org.apache.zest.library.scheduler.Task;

public interface BindableTask extends Task, Identity
{
    @Optional
    Association<Identity> entity();
}
