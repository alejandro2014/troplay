package troplay;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
class GameVariables {
    public enum Language { SPANISH, ENGLISH };

    private Language language;
    private Integer playersNo;

    public GameVariables() {
        language = Language.SPANISH;
        playersNo = 1;
    }
}
