package core.web;

import core.database.API;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class PicturesDownloader {

    public static final String PROFILE_PICTURE = API.HOST + "profile-picture";
    private static final String gameNameFolder = "MemoStickRescue";
    private static final String picturesFolder = "pictures";
    private static final String profilePicturesFolder = "profile";
    private static final String basicPath = FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + picturesFolder + "/" + profilePicturesFolder + "/";

    public String download(String pictureName, int index) throws Exception {
        checkDirectories();
        String newName = "picture" + index + ".jpg";
        Files.copy(new URL(PROFILE_PICTURE + "/" + pictureName).openStream(), Paths.get(basicPath + newName), StandardCopyOption.REPLACE_EXISTING);
        return basicPath + newName;
    }

    private void checkDirectories() {
        File mainDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory(), gameNameFolder);
        File picturesDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder, picturesFolder);
        File profileDirectory = new File(FileSystemView.getFileSystemView().getDefaultDirectory() + "/" + gameNameFolder + "/" + picturesFolder, profilePicturesFolder);
        checkIfDirectoryExists(mainDirectory, picturesDirectory, profileDirectory);
    }

    private void checkIfDirectoryExists(File... directory) {
        for (File file: directory) {
            if (!file.exists()) file.mkdirs();
        }
    }
}