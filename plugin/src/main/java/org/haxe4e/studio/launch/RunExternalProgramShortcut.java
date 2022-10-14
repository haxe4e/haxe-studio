/*
 * Copyright 2021-2022 by the Haxe4E authors.
 * SPDX-License-Identifier: EPL-2.0
 */
package org.haxe4e.studio.launch;

import static net.sf.jstuff.core.validation.NullAnalysisHelper.*;

import org.eclipse.core.externaltools.internal.IExternalToolConstants;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.debug.core.DebugPlugin;
import org.eclipse.debug.core.ILaunchConfiguration;
import org.eclipse.debug.ui.DebugUITools;
import org.eclipse.debug.ui.ILaunchShortcut;
import org.eclipse.jdt.annotation.NonNull;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.part.FileEditorInput;
import org.haxe4e.studio.HaxeStudioPlugin;

import de.sebthom.eclipse.commons.ui.Dialogs;
import de.sebthom.eclipse.commons.ui.UI;

/**
 * @author Sebastian Thomschke
 */
@SuppressWarnings("restriction")
public class RunExternalProgramShortcut implements ILaunchShortcut {

   @Override
   public void launch(final IEditorPart editor, final String mode) {
      final var editorInput = editor.getEditorInput();
      if (editorInput instanceof final FileEditorInput fileInput) {
         launchExternalProgram(fileInput.getFile(), mode);
      }
   }

   @Override
   public void launch(final ISelection selection, final String mode) {
      if (selection instanceof IStructuredSelection) {
         final var firstElement = ((IStructuredSelection) selection).getFirstElement();
         if (firstElement instanceof @NonNull final IFile file) {
            launchExternalProgram(file, mode);
         }
      }
   }

   private void launchExternalProgram(final IFile programFile, final String mode) {
      final var launchMgr = DebugPlugin.getDefault().getLaunchManager();
      final var launchConfigType = launchMgr.getLaunchConfigurationType(IExternalToolConstants.ID_PROGRAM_LAUNCH_CONFIGURATION_TYPE);

      final var project = asNonNull(programFile.getProject());
      final var location = "${workspace_loc:/" + project.getName() + "/" + programFile.getProjectRelativePath() + "}";
      final var workDir = "${workspace_loc:/" + project.getName() + "}";
      try {
         // use an existing launch config if available
         for (final ILaunchConfiguration cfg : launchMgr.getLaunchConfigurations(launchConfigType)) {
            if (cfg.getAttribute(IExternalToolConstants.ATTR_LOCATION, "").equals(location) //
               && cfg.getAttribute(IExternalToolConstants.ATTR_WORKING_DIRECTORY, "").equals(workDir) //
            ) {
               DebugUITools.launch(cfg, mode);
               return;
            }
         }

         // create a new launch config
         final var newLaunchConfig = launchConfigType.newInstance(null, launchMgr.generateLaunchConfigurationName(project.getName() + " ("
            + programFile.getName() + ")"));
         newLaunchConfig.setAttribute(IExternalToolConstants.ATTR_LOCATION, location);
         newLaunchConfig.setAttribute(IExternalToolConstants.ATTR_WORKING_DIRECTORY, workDir);

         if (Window.OK == DebugUITools.openLaunchConfigurationDialog(UI.getShell(), newLaunchConfig,
            org.eclipse.ui.externaltools.internal.model.IExternalToolConstants.ID_EXTERNAL_TOOLS_LAUNCH_GROUP, null)) {
            newLaunchConfig.doSave();
         }
      } catch (final CoreException ex) {
         Dialogs.showStatus("Failed to create Launch configuration", HaxeStudioPlugin.status().createError(ex), true);
      }
   }
}
