/*
 * Copyright 2021-2022 by the Haxe4E authors.
 * SPDX-License-Identifier: EPL-2.0
 */
package org.haxe4e.studio;

import org.eclipse.core.runtime.preferences.ConfigurationScope;
import org.eclipse.ui.application.IWorkbenchConfigurer;
import org.eclipse.ui.internal.ide.application.DelayedEventsProcessor;
import org.eclipse.ui.internal.ide.application.IDEWorkbenchAdvisor;
import org.eclipse.ui.preferences.ScopedPreferenceStore;

/**
 * @author Sebastian Thomschke
 */
@SuppressWarnings("restriction")
public class HaxeStudioWorkbenchAdvisor extends IDEWorkbenchAdvisor {

   public HaxeStudioWorkbenchAdvisor(final DelayedEventsProcessor processor) {
      super(processor);
   }

   /**
    * Executed before {@link #preStartup()}
    */
   @Override
   public void initialize(final IWorkbenchConfigurer configurer) {
      super.initialize(configurer);

      configureWorkspaceDefaults();
   }

   private void configureWorkspaceDefaults() {

      final var resPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.core.resources");
      resPrefs.setDefault("encoding", "UTF-8");
      resPrefs.setDefault("refresh.lightweight.enabled", true);
      resPrefs.setDefault("missingEncodingMarkerSeverity", -1); // -1=IGNORE, 0=INFO, 1=WARN, 2=ERROR

      final var uiPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.ui");
      uiPrefs.setDefault("ENABLE_ANIMATIONS", false);
      uiPrefs.setDefault("SHOW_MEMORY_MONITOR", true);
      uiPrefs.setDefault("SHOW_TEXT_ON_PERSPECTIVE_BAR", false);

      final var uiConsolePrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.ui.console");
      uiConsolePrefs.setDefault(".P_CONSOLE_WORD_WRAP", false); // for https://github.com/mihnita/ansi-econsole

      final var uiDebugPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.debug.ui");
      uiDebugPrefs.setDefault("Console.limitConsoleOutput", false); // for https://github.com/mihnita/ansi-econsole

      final var uiEditorsPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.ui.editors");
      uiEditorsPrefs.setDefault("lineNumberRuler", true);
      uiEditorsPrefs.setDefault("printMargin", true);
      uiEditorsPrefs.setDefault("printMarginAllowOverride", true);
      uiEditorsPrefs.setDefault("printMarginColumn", 120);
      uiEditorsPrefs.setDefault("spacesForTabs", false);

      uiEditorsPrefs.setDefault("quickdiff.characterMode", true);
      uiEditorsPrefs.setDefault("quickdiff.defaultProvider", "org.eclipse.egit.ui.internal.decorators.GitQuickDiffProvider");

      uiEditorsPrefs.setDefault("showWhitespaceCharacters", true);
      uiEditorsPrefs.setDefault("showLeadingIdeographicSpaces", false);
      uiEditorsPrefs.setDefault("showLeadingSpaces", false);
      uiEditorsPrefs.setDefault("showLeadingTabs", false);
      uiEditorsPrefs.setDefault("showEnclosedIdeographicSpaces", false);
      uiEditorsPrefs.setDefault("showEnclosedSpaces=", false);
      uiEditorsPrefs.setDefault("showEnclosedTabs", false);
      uiEditorsPrefs.setDefault("showTrailingIdeographicSpaces", true);
      uiEditorsPrefs.setDefault("showTrailingSpaces", true);
      uiEditorsPrefs.setDefault("showTrailingTabs", true);
      uiEditorsPrefs.setDefault("showCarriageReturn", false);
      uiEditorsPrefs.setDefault("showLineFeed", false);
      uiEditorsPrefs.setDefault("whitespaceCharacterAlphaValue", 40);

      final var uiWbPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.ui.workbench");
      uiWbPrefs.setDefault("showCommandKeysForMouseEvents", true); // https://www.eclipse.org/eclipse/news/4.13/platform.php#show-keybinding

      final var cmpPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.compare");
      cmpPrefs.setDefault("org.eclipse.compare.IgnoreWhitespace", true);
      cmpPrefs.setDefault("org.eclipse.compare.UseOutlineView", true);
      cmpPrefs.setDefault("org.eclipse.compare.OpenStructureCompare", false); // to prevent https://bugs.eclipse.org/bugs/show_bug.cgi?id=509820

      final var teamPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.team.ui");
      teamPrefs.setDefault("org.eclipse.team.ui.compress_folders", true);
      teamPrefs.setDefault("org.eclipse.team.ui.default_layout", "org.eclipse.team.ui.compressed_layout");
      teamPrefs.setDefault("org.eclipse.team.ui.run_import_in_background_", true);

      final var egitCorePrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.egit.core");
      egitCorePrefs.setDefault("core_defaultRepositoryDir", "${workspace_loc}");

      final var egitUiPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "org.eclipse.egit.ui");
      egitUiPrefs.setDefault("decorator_filetext_decoration", "{name}");
      egitUiPrefs.setDefault("decorator_foldertext_decoration", "{name}");
      egitUiPrefs.setDefault("decorator_projecttext_decoration", "{name} [{repository }{branch}{ branch_status}]");
      egitUiPrefs.setDefault("decorator_submoduletext_decoration", "{name} [{branch}{ branch_status}]{ short_message}");
      egitUiPrefs.setDefault("decorator_show_dirty_icon", true);
      egitUiPrefs.setDefault("show_push_success_dialog", false);

      final var easyShellPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "de.anbos.eclipse.easyshell.plugin");
      easyShellPrefs.setDefault("v2_2_001/MIGRATED", true);

      final var intentGuidePrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "net.certiv.tools.indentguide");
      intentGuidePrefs.setDefault("indentguide.enabled", true);
      intentGuidePrefs.setDefault("indentguide.line_style", 3);

      final var ttdPrefs = new ScopedPreferenceStore(ConfigurationScope.INSTANCE, "net.sf.ttd.core");
      ttdPrefs.setDefault("net.sf.ttd.core.config.impl.SerializerVersion1.count", 2);
      ttdPrefs.setDefault("net.sf.ttd.core.config.impl.SerializerVersion1.image0TOP_LEFT", "Exclamation Blue");
      ttdPrefs.setDefault("net.sf.ttd.core.config.impl.SerializerVersion1.image1TOP_LEFT", "Exclamation");
      ttdPrefs.setDefault("net.sf.ttd.core.config.impl.SerializerVersion1.taskTagName0", "todo");
      ttdPrefs.setDefault("net.sf.ttd.core.config.impl.SerializerVersion1.taskTagName1", "fixme");
   }
}
