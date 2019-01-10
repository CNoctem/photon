package com.konishy.photon.db;

import com.drew.metadata.Directory;
import com.konishy.photon.meta.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileFilter;
import java.util.*;

public class PhotonCollector {

    private static Logger log = LoggerFactory.getLogger(PhotonCollector.class);

    private Map<String, Meta> coll = new HashMap<>();

    private List<File> directories = new ArrayList<>();

    public PhotonCollector() {

    }

    public void 

    public PhotonCollector addDirectory(File dir, boolean recursive) {
        if (!dir.isDirectory()) {
            log.warn("'{}' is not a directory. Skipping", dir.getAbsolutePath());
            return this;
        }
        directories.add(dir);
        if (recursive) {
            for (File subdir : dir.listFiles(File::isDirectory)) {
                addDirectory(subdir, true);
            }
        }
        return this;
    }

    public PhotonCollector build() {
        for (File dir : directories) {
            for (File file : dir.listFiles(File::isFile)) {
                Meta m = new Meta(file);
                coll.put(file.getAbsolutePath(), m);
            }
        }
        return this;
    }

}
