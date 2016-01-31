package org.jsoftware.dbpatch.command;

import org.jsoftware.dbpatch.config.EnvSettings;
import org.jsoftware.dbpatch.impl.CloseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Pattern;


/**
 * Command: Display help
 *
 * @author szalik
 */
public class HelpCommand extends AbstractCommand {
    private static final Pattern TASK_PREFIX_PATTERN = Pattern.compile("\\[prefix:task\\]");
    private static final Pattern PROPERTY_PREFIX_PATTERN = Pattern.compile("\\[prefix:property\\]");
    private final String taskPrefix, propertyPrefix;

    public HelpCommand(EnvSettings envSettings, String taskPrefix, String propertyPrefix) {
        super(envSettings);
        this.taskPrefix = taskPrefix;
        this.propertyPrefix = propertyPrefix;
    }

    public void execute() throws CommandExecutionException {
        InputStream in = getClass().getResourceAsStream("/dbpatch-help.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String s;
            while ((s = br.readLine()) != null) {
                s = replace(s);
                System.out.println(s);
            }
        } catch (IOException e) {
            throw new CommandExecutionException(e.getMessage(), e);
        } finally {
            CloseUtil.close(br);
            CloseUtil.close(in);
        }
    }

    private String replace(String text) {
        text = TASK_PREFIX_PATTERN.matcher(text).replaceAll(taskPrefix);
        text = PROPERTY_PREFIX_PATTERN.matcher(text).replaceAll(propertyPrefix);
        return text;
    }

}
