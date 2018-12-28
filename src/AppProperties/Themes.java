package AppProperties;

public enum Themes {
    Glow, Dark;

    String getName() {
        switch (this) {
            case Dark:
                return "Dark";
            default:
                return "Glow";
        }
    }

    static Themes getTypeByName(String name) {
        if (name.equals(Dark.name())) {
            return Dark;
        }
        return Glow;
    }

    public String getRegularPipe() {
        switch (this) {
            case Dark:
                return "./resources/TubeCorner-R02.png";
            default:
                return "./resources/Tube-R01.png";
        }
    }

    public String getAnglePipe() {
        switch (this) {
            case Dark:
                return "./resources/TubeCorner-R02.png";
            default:
                return "./resources/TubeCorner-R01.png";
        }
    }

    public String getBackgroundImage() {
        switch (this) {
            case Dark:
                return "./resources/Wall-R01.jpg";
            default:
                return "./resources/Wall-R02.jpg";
        }
    }

    public String getEndImage() {
        switch (this) {
            case Dark:
                return "./resources/End-R01A.png";
            default:
                return "./resources/End-R01.png";
        }
    }

    public String getStartImage() {
        switch (this) {
            case Dark:
                return "./resources/End-R01A.png";
            default:
                return "./resources/Start-R01.png";
        }
    }
}