package name.panitz.game2d.flappypanitz;

public enum Character {
    CURRENT_PANITZ("current_panitz.png", "Prof. Panitz"),
    HUT_PANITZ("hut_panitz.png", "Hut-Panitz"),
    MASKEN_PANITZ("masken_panitz.png", "Corona-Panitz"),
    BABY_PANITZ("baby_panitz.png", "Baby Panitz"),
    TEEN_PANITZ("teen_panitz.png", "Teenie Panitz"),
    ZUG_PANITZ("zug_panitz.png", "Zug Panitz"),
    EINSA_PANITZ("1a_panitz.png", "Eins-A Panitz"),
    BAD_HAIR_DAY_PANITZ("bad_hair_day_panitz.png", "Bad Hair Day Panitz"),
    FAMILIEN_PANITZ("familien_panitz.png", "Familien Panitz"),
    SONNTAGSANZUG_PANITZ("sonntagsanzug_panitz.png", "Sonntagsanzug Panitz"),
    WEIHNACHTEN_PANITZ("weihnachten_panitz.png", "Weihnachtsmann Panitz"),
    WEITSICHT_PANITZ("weitsicht_panitz.png", "Weitsicht Panitz"),
    HAPPY_PANITZ("happy_panitz.png", "Happy Panitz");

    private final String fileLocation;
    private final String name;

    Character(String fileLocation, String name) {
        this.fileLocation = fileLocation;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private final String location = "/assets/edits/exports/scaled/";

    public String get32pxResolution() {
        return location + "32px/" + fileLocation;
    }

    @SuppressWarnings("unused") // Wer weiß, vielleicht brauche ich die ja eines Tages noch
    public String get64pxResolution() {
        return location + "64px/" + fileLocation;
    }
    public String get128pxResolution() {
        return location + "128px/" + fileLocation;
    }
}
