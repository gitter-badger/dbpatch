package org.jsoftware.impl;

import java.sql.Connection;
import java.sql.SQLWarning;

import javax.swing.JEditorPane;

import org.jsoftware.config.Patch;
import org.jsoftware.config.dialect.PatchExecutionResult;
import org.jsoftware.impl.extension.Extension;

/**
 * Dolna belka z wynikami
 * @author mgruszecki
 */
public class ResultDisplay extends JEditorPane implements Extension {
	private static final long serialVersionUID = -6937849129583028112L;
	private StringBuilder buf;
	
	public ResultDisplay() {
		super("text/html", null);
		setEditable(false);
		setAutoscrolls(true);
		buf = new StringBuilder("<html><body>");
	}
	
	// DbPatch Extension
	public void afterPatching(Connection connection) {	}
	public void beforePatch(Connection connection, Patch patch) {
		addInfo("com.patching.start", new Object[] {patch.getName(), patch.getFile().getName()});	
	}
	public void beforePatchStatement(Connection connection, Patch patch, PatchStatement statement) {	}
	public void beforePatching(Connection connection) {	}
	public void afterPatch(Connection connection, Patch patch, Exception ex) {
		if (ex == null) {
			addInfo("com.patching.done.ok", new Object[] {patch.getName(), patch.getFile().getName()});
		} else {
			addText(Messages.msg("com.patching.done.error", new Object[] {patch.getName(), patch.getFile().getName()}), "#ff0000");
		}
		addText("<br />", null);
	}

	public void afterPatchStatement(Connection connection, Patch patch, PatchExecutionResult result) {
		addText(result.getPatchStatement().getCode() + "<br />", null);
		if (! result.isSuccess()) {
			addText("<b>" + result.getCause().getLocalizedMessage() + "</b>", "#ff0000");
			addText("<br />", null);
		} else {
			SQLWarning warn = result.getSqlWarnings();
			while(warn != null) {
				addText(warn.getLocalizedMessage(), "#f4850d");
				addText("<br />", null);
			}
			if (result.getDmlType() != null) {
				addText(Messages.msg("com.patch." + result.getDmlType().name().toLowerCase(), new Object[] { Integer.valueOf(result.getDmlCount())}), "#18f40d");
			} else {
				addText(Messages.msg("com.patch.otherType"), "#18f40d");
			}
			addText("<br />", null);
		}
	}
	
	private synchronized void addText(String text, String color) {
		text = text.trim();
		if (text.length() > 0) {
			if (color != null) {
				buf.append("<font color=\"").append(color).append("\">");
				buf.append(text);
				buf.append("</font>");
			} else {
				buf.append(text);
			}
			setText(buf.toString() + "</body></html>");
		}
	}
	
	public void addInfo(String key, Object[] args) {
		addText(Messages.msg(key, args), "#130df4");
		addText("<br />", null);
	}
}