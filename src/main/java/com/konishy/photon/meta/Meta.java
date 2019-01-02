package com.konishy.photon.meta;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class Meta {

    private static Logger log = LoggerFactory.getLogger(Meta.class);

    private String json;

    public Meta(File imageFile) {
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(imageFile);
            json = createRootNode(metadata).toString();
        } catch (ImageProcessingException | IOException e) {
            log.error("", e);
        }
    }

    public String getJson() {
        return json;
    }

    private ObjectNode createRootNode(Metadata metadata) {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode rootNode = mapper.createObjectNode();
        for (Directory d : metadata.getDirectories()) {
            JsonNode dNode = createDirNode(d, mapper);
            rootNode.put(d.getName(), dNode);
        }
        return rootNode;
    }

    private JsonNode createDirNode(Directory d, ObjectMapper mapper) {
        ArrayNode dirNode = mapper.createArrayNode();
        for (Tag t : d.getTags()) {
           ObjectNode tagNode = mapper.createObjectNode();
           tagNode.put(t.getTagName(), t.getDescription());
           dirNode.add(tagNode);
        }
        return dirNode;
    }

}
