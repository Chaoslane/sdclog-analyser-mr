package com.udbac.hadoop.common;

import org.apache.commons.lang.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.PathFilter;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by root on 2017/3/17.
 */
public class RegexFilter extends Configured implements PathFilter {
    private static Logger logger = Logger.getLogger(RegexFilter.class);

    @Override
    public Configuration getConf() {
        return super.getConf();
    }

    @Override
    public boolean accept(Path path) {
        try {
            FileSystem fs = FileSystem.get(getConf());
            String fileReg = getConf().get("filename.pattern");
            Pattern pattern = Pattern.compile(fileReg);

            if (fs.isDirectory(path)) {
                return true;
            } else if (fs.getFileStatus(path).getLen() < 100) {
                return false;
            }else {
                Matcher m = pattern.matcher(path.toString());
                if (m.matches()) {
                    logger.info(path.toString()+" is matched");
                }
                return m.matches();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

}