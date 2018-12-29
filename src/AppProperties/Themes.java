package AppProperties;

public enum Themes {
	Gray, Blue;

    String getName() {
        switch (this) {
            case Blue:
                return "Blue";
            default:
                return "Gray";
        }
    }

    static Themes getTypeByName(String name) {
        if (name.equals(Blue.name())) {
            return Blue;
        }
        return Gray;
    }

    public String getRegularPipe() {
        switch (this) {
            case Blue:
                return "./resources/TubeCorner-R02.png";
            default:
                return "./resources/Tube-R01.png";
        }
    }

    public String getAnglePipe() {
        switch (this) {
            case Blue:
                return "./resources/TubeCorner-R02.png";
            default:
                return "./resources/TubeCorner-R01.png";
        }
    }

    public String getBackgroundImage() {
        switch (this) {
            case Blue:
                return "./resources/Wall-R01.jpg";
            default:
                return "./resources/Wall-R02.jpg";
        }
    }

    public String getEndImage() {
        switch (this) {
            case Blue:
                return "./resources/End-R01A.png";
            default:
                return "./resources/End-R01.png";
        }
    }

    public String getStartImage() {
        switch (this) {
            case Blue:
                return "./resources/End-R01A.png";
            default:
                return "./resources/Start-R01.png";
        }
    }
}