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
                return "./resources/Blue.png";
            default:
                return "./resources/Gray.png";
        }
    }

    public String getAnglePipe() {
        switch (this) {
            case Blue:
                return "./resources/BlueCorner.png";
            default:
                return "./resources/GrayCorner.png";
        }
    }

    public String getBackgroundImage() {
        switch (this) {
            case Blue:
                return "./resources/BlueBackGround.jpg";
            default:
                return "./resources/GrayBackGround.jpg";
        }
    }

    public String getEndImage() {
        switch (this) {
            case Blue:
                return "./resources/BLueSE.png";
            default:
                return "./resources/GraySE.png";
        }
    }

    public String getStartImage() {
        switch (this) {
            case Blue:
                return "./resources/BLueSE.png";
            default:
                return "./resources/GraySE.png";
        }
    }
    
    public String getMusic() {
        switch (this) {
            case Blue:
                return "resources/Blue.mp3";
            default:
                return "resources/Gray.mp3";
        }
    }
}