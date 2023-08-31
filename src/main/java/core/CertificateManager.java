package core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class CertificateManager {

    private final File certificateDirectory;
    private final FileHandle sourceFile, destinationFile;
    private final String gameName = "/MemoStickRescue";
    private final String certificate = "certificate";
    private final String cacerts = "/cacerts";

    public CertificateManager() {
        this.certificateDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + gameName, certificate);
        this.sourceFile = Gdx.files.internal(certificate + cacerts);
        this.destinationFile = Gdx.files.absolute(certificateDirectory.getAbsolutePath() + cacerts);
    }

    public void set() {
        if (!certificateDirectory.exists()) {
            certificateDirectory.mkdirs();
        }
        sourceFile.copyTo(destinationFile);
        System.setProperty("javax.net.ssl.trustStore", certificateDirectory.getAbsolutePath() + cacerts);
    }

}