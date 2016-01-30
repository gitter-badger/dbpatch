package org.jsoftware.dbpatch.impl.extension;

import org.jsoftware.dbpatch.config.AbstractPatch;
import org.jsoftware.dbpatch.config.Patch;
import org.jsoftware.dbpatch.config.RollbackPatch;
import org.jsoftware.dbpatch.config.dialect.PatchExecutionResult;
import org.jsoftware.dbpatch.impl.PatchStatement;

import java.sql.Connection;
import java.sql.SQLException;

public interface Extension {

    /**
     * Before patching procedure starts
     */
    void beforePatching(Connection connection);

    /**
     * After patching process ends
     */
    void afterPatching(Connection connection);

    /**
     * Before the patch is executed
     */
    void beforePatch(Connection connection, Patch patch);

    /**
     * After the patch is executed
     *
     * @throws SQLException
     */
    void afterPatch(Connection connection, Patch patch, Exception ex) throws SQLException;

    /**
     * Before patch's statement is executed
     */
    void beforePatchStatement(Connection connection, AbstractPatch patch, PatchStatement statement);

    /**
     * After patch's statement is executed
     */
    void afterPatchStatement(Connection connection, AbstractPatch patch, PatchExecutionResult result);

    /**
     * Before the patch rollback is executed
     */
    void beforeRollbackPatch(Connection connection, RollbackPatch patch);

    /**
     * After the patch rollback is executed
     *
     * @throws SQLException
     */
    void afterRollbackPatch(Connection connection, RollbackPatch patch, Exception ex) throws SQLException;

}

