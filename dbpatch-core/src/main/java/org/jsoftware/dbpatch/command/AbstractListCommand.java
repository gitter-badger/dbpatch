package org.jsoftware.dbpatch.command;

import org.jsoftware.dbpatch.config.AbstractPatch;
import org.jsoftware.dbpatch.config.EnvSettings;
import org.jsoftware.dbpatch.config.Patch;
import org.jsoftware.dbpatch.config.PatchScanner;
import org.jsoftware.dbpatch.impl.DuplicatePatchNameException;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


/**
 * Show list of patches
 *
 * @author szalik
 */
abstract class AbstractListCommand<P extends AbstractPatch> extends AbstractSingleConfDbPatchCommand {
    static final int SPACES = 46;

    public AbstractListCommand(EnvSettings envSettings) {
        super(envSettings);
    }

    /**
     * @return patches to apply
     * @throws java.io.IOException
     * @throws java.sql.SQLException
     * @throws DuplicatePatchNameException
     */
    private List<Patch> generatePatchListAll() throws IOException, SQLException, DuplicatePatchNameException {
        PatchScanner scanner = configurationEntry.getPatchScanner();
        List<Patch> patches = scanner.scan(directory, configurationEntry.getPatchDirs().split(","));
        manager.updateStateObjectAll(patches);
        return patches;
    }

    abstract protected List<P> generateList(List<Patch> inList) throws IOException, SQLException, DuplicatePatchNameException;

    List<P> getList() throws DuplicatePatchNameException, SQLException, IOException {
        return generateList(generatePatchListAll());
    }


}
