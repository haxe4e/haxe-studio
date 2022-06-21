/*
 * Copyright 2021-2022 by the Haxe4E authors.
 * SPDX-License-Identifier: EPL-2.0
 */
package org.haxe4e.studio;

import java.net.URL;

import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.equinox.app.IApplicationContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.internal.Workbench;
import org.eclipse.ui.internal.WorkbenchPlugin;
import org.eclipse.ui.internal.ide.ChooseWorkspaceDialog;
import org.eclipse.ui.internal.ide.IDEInternalPreferences;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.ide.application.DelayedEventsProcessor;
import org.eclipse.ui.internal.ide.application.IDEApplication;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * @author Sebastian Thomschke
 */
@SuppressWarnings("restriction")
public class HaxeStudioApplication extends IDEApplication {

   /**
    * Only overriding {@link IDEApplication#start(IApplicationContext)} to configure custom WorkbenchAdvisor
    */
   @Override
   public Object start(final IApplicationContext ctx) throws Exception {
      Job.getJobManager().suspend();

      final var display = createDisplay();

      try {
         final var processor = new DelayedEventsProcessor(display);

         final var shell = WorkbenchPlugin.getSplashShell(display);
         if (shell != null) {
            shell.setText(ChooseWorkspaceDialog.getWindowTitle());
            shell.setImages(Window.getDefaultImages());
         }

         final var instanceLocationCheck = checkInstanceLocation(shell, ctx.getArguments());
         if (instanceLocationCheck != null) {
            WorkbenchPlugin.unsetSplashShell(display);
            return instanceLocationCheck;
         }

         final var rc = PlatformUI.createAndRunWorkbench(display, new HaxeStudioWorkbenchAdvisor(processor));

         if (rc != PlatformUI.RETURN_RESTART)
            return EXIT_OK;

         return EXIT_RELAUNCH.equals(Integer.getInteger(Workbench.PROP_EXIT_CODE)) ? EXIT_RELAUNCH : EXIT_RESTART;

      } finally {
         display.dispose();

         final var instanceLoc = Platform.getInstanceLocation();
         if (instanceLoc != null) {
            instanceLoc.release();
         }
      }
   }

   @Override
   protected ReturnCode checkValidWorkspace(final Shell shell, final URL url) {

      /* prevent "Workspace was written with an older version of the product" warning.
       * see https://stackoverflow.com/questions/63029131/is-there-a-way-to-force-an-update-to-the-eclipse-workspace
       */
      final var workbenchPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, IDEWorkbenchPlugin.IDE_WORKBENCH);
      workbenchPrefs.setValue(IDEInternalPreferences.WARN_ABOUT_WORKSPACE_INCOMPATIBILITY, false);

      return super.checkValidWorkspace(shell, url);
   }
}
