package com.konishy.photon;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.imaging.jpeg.JpegMetadataReader;
import com.drew.imaging.jpeg.JpegProcessingException;
import com.drew.imaging.jpeg.JpegSegmentMetadataReader;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifIFD0Directory;
import com.drew.metadata.exif.ExifReader;
import com.drew.metadata.iptc.IptcReader;
import com.konishy.photon.meta.Json;
import com.konishy.photon.meta.Meta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

public class Photon {

    private static Logger log = LoggerFactory.getLogger(Photon.class);

    public static void main(String[] args)
    {
        File file = new File("/home/ekovger/Pictures/180102Tarko/DSC_9403.NEF");
        try {
            Metadata metadata = ImageMetadataReader.readMetadata(file);

            print(metadata, "Using ImageMetadataReader");

            // obtain the Exif directory
            Directory directory
                    = metadata.getFirstDirectoryOfType(ExifIFD0Directory.class);

            // query the tag's value
            Date date
                    = directory.getDate(ExifIFD0Directory.TAG_DATETIME_ORIGINAL);

            log.info("Datetime original {}", date);


            Meta m = new Meta(file);
            String json = m.getJson();

            Json j = new Json(json);
            System.out.println(j.get("File", "File Name"));
        } catch (ImageProcessingException | IOException e) {
            print(e);
        }

        //
        // APPROACH 3: SPECIFIC METADATA TYPE
        //
        // If you only wish to read a subset of the supported metadata types, you can do this by
        // passing the set of readers to use.
        //
        // This currently only applies to JPEG file processing.
        //
        try {
            // We are only interested in handling
            Iterable<JpegSegmentMetadataReader> readers = Arrays.asList(new ExifReader(), new IptcReader());

            Metadata metadata = JpegMetadataReader.readMetadata(file, readers);

            print(metadata, "Using JpegMetadataReader for Exif and IPTC only");
        } catch (JpegProcessingException | IOException e) {
            print(e);
        }
    }

    /**
     * Write all extracted values to stdout.
     */
    private static void print(Metadata metadata, String method)
    {
        for (Directory directory : metadata.getDirectories()) {
            for (Tag tag : directory.getTags()) {
                System.out.println(tag);
            }

            for (String error : directory.getErrors()) {
                System.err.println("ERROR: " + error);
            }
        }
    }

    private static void print(Exception exception)
    {
        System.err.println("EXCEPTION: " + exception);
    }

}
