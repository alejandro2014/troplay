package troplay;

import lombok.Builder;
import lombok.Data;
import troplay.enums.MainStatuses;

@Data
@Builder
public class TransitionInfo {
    private MainStatuses currentStatus;
    private int event;
    private MainStatuses nextStatus;
    private Class classToExecute;
}
