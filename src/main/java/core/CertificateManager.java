package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class CertificateManager {

    private final FileHandle sourceFile, destinationFile;
    private final String certificate = "certificate/";
    private final String cacerts = "cacerts";

    public CertificateManager() {
        this.sourceFile = Gdx.files.internal(certificate + cacerts);
        this.destinationFile = Gdx.files.absolute(cacerts);
    }

    public void set() {
        sourceFile.copyTo(destinationFile);
        System.setProperty("javax.net.ssl.trustStore", cacerts);
    }

}