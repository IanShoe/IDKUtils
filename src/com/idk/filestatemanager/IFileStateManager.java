/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.idk.filestatemanager;

import java.io.File;
import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public interface IFileStateManager {

    public static class FileState implements Serializable {

        String checksum;
        String fileName;
        boolean exists;

        public FileState(String fileName, String checksum, boolean exists) {
            this.checksum = checksum;
            this.fileName = fileName;
            this.exists = exists;
        }

        public boolean isSameFile(File file) {
            if (file == null) {
                return false;
            }
            return file.getAbsolutePath().equals(this.fileName);
        }
    }

    public void removeFromFileMap(String uuid);

    public void removeFilesFromFileMap(Collection<File> failedFiles, String uuid);

    public Collection<File> determineFilesToProcess(Collection<File> files, String uuid);

    public Map<String, Collection<IFileStateManager.FileState>> getFileMap();
}
