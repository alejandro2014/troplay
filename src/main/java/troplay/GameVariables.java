package troplay;

class GameVariables {
    public enum Language { SPANISH, ENGLISH };

    private Language language;
    private Integer playersNo;

    public GameVariables() {
        language = Language.SPANISH;
        playersNo = 1;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Integer getPlayersNo() {
        return playersNo;
    }

    public void setPlayersNo(Integer playersNo) {
        this.playersNo = playersNo;
    }
}
