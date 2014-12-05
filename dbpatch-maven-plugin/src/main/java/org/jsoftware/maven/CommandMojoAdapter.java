package org.jsoftware.maven;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.logging.Log;
import org.jsoftware.command.AbstractCommand;
import org.jsoftware.command.CommandExecutionException;
import org.jsoftware.command.CommandFailureException;
import org.jsoftware.config.ConfigurationEntry;
import org.jsoftware.log.LogFactory;

import java.io.File;

/**
 * Abstract mojo for dbPatch plugin goals
 *
 * @author szalik
 */
public class CommandMojoAdapter<C extends AbstractCommand> extends AbstractMojo {
    protected final C command;

    /**
     * Configuration file
     *
     * @parameter
     */
    protected File configFile;

    /**
     * Daatabase and patch configuration
     *
     * @parameter
     * @see ConfigurationEntry's fields
     */
    protected ConfigurationEntry conf;

    /**
     * Project directory
     *
     * @parameter default-value="${basedir}"
     * @required
     * @readonly
     */
    protected File directory;

    protected CommandMojoAdapter(C command) {
        this.command = command;
    }


    @Override
    public void setLog(Log log) {
        super.setLog(log);
        if (log != null) {
            LogFactory.init(new MavenLog(log));
        }
    }

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        Log log = getLog();
        if (System.getProperty("maven.dbpatch.skip") != null) {
            log.debug("dbpatch skipped");
            return;
        }
        command.setConf(conf);
        command.setDirectory(directory);
        command.setConfigFile(configFile);
        setup(command);
        try {
            command.execute();
        } catch (CommandExecutionException e) {
            MojoExecutionException mojoExecutionException = new MojoExecutionException(command, e.getMessage(), "");
            mojoExecutionException.initCause(e);
            throw mojoExecutionException;
        } catch (CommandFailureException e) {
            MojoFailureException mojoFailureException = new MojoFailureException(command, e.getMessage(), "");
            mojoFailureException.initCause(e);
            throw mojoFailureException;
        }
    }

    protected void setup(C command) {

    }
}

class MavenLog implements org.jsoftware.log.Log {
    private final org.apache.maven.plugin.logging.Log mLog;

    MavenLog(Log mLog) {
        this.mLog = mLog;
    }

    public void trace(String msg, Throwable e) {
        if (e == null) {
            mLog.debug(msg);
        } else {
            mLog.debug(msg, e);
        }
    }

    public void warn(String msg) {
        mLog.warn(msg);
    }

    public void info(String msg) {
        mLog.info(msg);
    }

    public void fatal(String msg) {
        mLog.error(msg);
    }

    public void debug(String msg) {
        mLog.debug(msg);
    }

    public void warn(String msg, Throwable e) {
        mLog.warn(msg, e);
    }

    public void fatal(String msg, Throwable e) {
        mLog.error(msg, e);
    }
}