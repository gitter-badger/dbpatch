package org.jsoftware.dbpatch.command;

import org.jsoftware.dbpatch.config.ConfigurationEntry;
import org.jsoftware.dbpatch.config.EnvSettings;
import org.jsoftware.dbpatch.log.LogFactory;

import java.io.File;

/**
 * Abstract plugin command
 *
 * @author szalik
 */
public abstract class AbstractCommand {
    protected final org.jsoftware.dbpatch.log.Log log = LogFactory.getInstance();
    protected final EnvSettings envSettings;
    private File configFile;
    private ConfigurationEntry conf;
    protected File directory;

    protected AbstractCommand(EnvSettings envSettings) {
        this.envSettings = envSettings;
    }

    public void setConfigFile(File configFile) {
        this.configFile = configFile;
    }

    public void setConf(ConfigurationEntry conf) {
        this.conf = conf;
    }

    public void setDirectory(File directory) {
        this.directory = directory;
    }

    public File getConfigFile() {
        if (configFile != null) {
            return configFile;
        }
        String cFile = System.getProperty(envSettings.getDbPatchFile());
        if (cFile != null) {
            return new File(cFile);
        } else {
            return null;
        }
    }

    public ConfigurationEntry getConf() {
        return conf;	/* findBugs ok - unwritten field (maven writes it) */
    }


    public abstract void execute() throws CommandExecutionException, CommandFailureException;

}
