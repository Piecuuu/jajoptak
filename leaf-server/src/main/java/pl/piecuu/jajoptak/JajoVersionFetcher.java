package pl.piecuu.jajoptak;

import org.galemc.gale.version.AbstractPaperVersionFetcher;

public class JajoVersionFetcher extends AbstractPaperVersionFetcher {

    public static final String DOWNLOAD_PAGE = "https://github.com/Piecuuu/jajoptak";

    public JajoVersionFetcher() {
        super(
            DOWNLOAD_PAGE,
            "Piecuu",
            "jajoptak",
            "piecuuu",
            "jajoptak",
            ApiType.GITHUB
        );
    }
}
