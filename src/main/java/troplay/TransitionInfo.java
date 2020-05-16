package troplay;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransitionInfo {
    private int currentStatus;
    private int event;
    private int nextStatus;
    private Class classToExecute;
}
