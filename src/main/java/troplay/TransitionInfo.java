package troplay;

import lombok.Builder;
import lombok.Data;
import troplay.enums.MainEvents;
import troplay.enums.MainStatuses;

@Data
@Builder
public class TransitionInfo {
    private MainStatuses currentStatus;
    private MainEvents event;
    private MainStatuses nextStatus;
    private Class classToExecute;
}
