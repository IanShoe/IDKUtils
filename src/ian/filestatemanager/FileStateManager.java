package ian.filestatemanager;

import ian.utils.FileUtil;
import java.io.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * File State Manager for local files to determine changes in files. Manages a
 * map of FileStateObjects with the UUID as it's key.
 *
 * @author shoemaki
 * @author snyderj
 */
public class FileStateManager implements IFileStateManager {

    private Map<String, Collection<FileState>> fileMap = new HashMap<String, Collection<FileState>>();
    private static final Logger LOG = Logger.getLogger(FileStateManager.class.getName());
    private String fileName = "fileState.ser";

    @PostConstruct
    private void startUp() {
        FileInputStream fis;
        ObjectInputStream in;
        File fileState = new File(fileName);
        if (fileState.exists()) {
            try {
                fis = new FileInputStream(fileName);
                in = new ObjectInputStream(fis);
                if (in != null) {
                    this.fileMap = (Map<String, Collection<FileState>>) in.readObject();
                }
                in.close();
            } catch (Exception ex) {
                LOG.log(Level.SEVERE, "Could not initiate FileStateManager startup");
                Logger.getLogger(FileStateManager.class.getName()).log(Level.SEVERE, null, ex);
                this.fileMap = new HashMap<String, Collection<FileState>>();
            }
        } else {
            try {
                fileState.createNewFile();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, "Could not create state file for FileStateManager");
                Logger.getLogger(FileStateManager.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @PreDestroy
    private void cleanUp() {
        FileOutputStream fos;
        ObjectOutputStream out = null;
        try {
            fos = new FileOutputStream(fileName);
            out = new ObjectOutputStream(fos);
            out.writeObject(this.fileMap);

        } catch (Throwable t) {
            LOG.log(Level.SEVERE, "Could not write file [{0}]", fileName);
            LOG.log(Level.SEVERE, "", t);
        } finally {
            if (out != null) {
                try {
                    out.flush();
                    out.close();
                } catch (IOException ex) {
                    Logger.getLogger(FileStateManager.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Method to remove the filemapping given a UUID
     *
     * @param uuid uuid of workflow to remove
     */
    @Override
    public void removeFromFileMap(String uuid) {
        if (this.fileMap.containsKey(uuid)) {
            this.fileMap.remove(uuid);
        }
    }

    @Override
    public void removeFilesFromFileMap(Collection<File> failedFiles, String uuid) {
        Collection<FileState> fileStates;
        try {
            fileStates = this.fileMap.get(uuid);
        } catch (Exception e) {
            return;
        }
        if (fileStates == null) {
            return;
        }
        for (File file : failedFiles) {
            for (FileState state : fileStates) {
                if (state.isSameFile(file)) {
                    fileStates.remove(state);
                }
            }
        }
        if (fileStates.isEmpty()) {
            this.fileMap.remove(uuid);
        } else {
            this.fileMap.put(uuid, fileStates);
        }
    }

    @Override
    public Map<String, Collection<FileState>> getFileMap() {
        return this.fileMap;
    }

    @Override
    public Collection<File> determineFilesToProcess(Collection<File> files, String uuid) {
        if (files == null || files.isEmpty()) {
            return files;
        }
        if (!fileMap.containsKey(uuid)) { // Initialize the map if nothing was there.
            fileMap.put(uuid, new ArrayList<FileState>());
        }

        final Collection<FileState> existingFileStates = new CopyOnWriteArrayList<FileState>(fileMap.get(uuid));
        for (FileState state : existingFileStates) {
            state.exists = false;
        }

        final Collection<File> filesToProcess = new ArrayList<File>();

        for (File file : files) {
            boolean fileStateExists = false;

            if (file.isDirectory()) {
                filesToProcess.addAll(this.determineFilesToProcess(Arrays.asList(file.listFiles()), uuid));
                continue;
            }
            statecheck:
            for (FileState state : existingFileStates) {
                if (state.isSameFile(file)) {
                    state.exists = true;
                    fileStateExists = true;
                    final String fileHash = FileUtil.sha1(file);
                    if (!fileHash.equals(state.checksum)) {
                        state.checksum = fileHash;
                        filesToProcess.add(file);
                        LOG.log(Level.INFO, "[{0}] has been updated", file.getAbsolutePath());
                    } else {
                        LOG.log(Level.INFO, "[{0}] is unchanged", file.getAbsolutePath());
                    }
                    break statecheck;
                }
            }

            if (!fileStateExists) { // File is new
                existingFileStates.add(new FileState(file.getAbsolutePath(), FileUtil.sha1(file), true));
                filesToProcess.add(file);
                LOG.log(Level.INFO, "[{0}] is a new, unprocessed file", file.getAbsolutePath());
            }
        }

        for (FileState state : existingFileStates) {
            if (!state.exists) { // File isn't tracked anymore
                existingFileStates.remove(state);
                LOG.log(Level.INFO, "[{0}] has been removed", state.fileName);
            }
        }
        fileMap.put(uuid, existingFileStates);
        return filesToProcess;
    }
}
