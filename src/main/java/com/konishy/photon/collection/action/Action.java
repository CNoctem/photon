package com.konishy.photon.collection;

import com.konishy.photon.meta.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public interface Action {

    void act(Meta m);

}

class CopyAction implements Action {

    private static Logger log = LoggerFactory.getLogger(CopyAction.class);

    private final String dest;

    public CopyAction(String destination) {
        dest = destination;
    }

    @Override
    public void act(Meta m) {
        try {
            log.info("{} -> {}", m.getImgFile().getAbsolutePath(), dest);
            Files.copy(
                    Paths.get(m.getImgFile().getAbsolutePath()),
                    Paths.get(dest + "/" + m.getImgFile().getName()),
                    StandardCopyOption.REPLACE_EXISTING,
                    StandardCopyOption.COPY_ATTRIBUTES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}