/*
 * Copyright 2021-2022 by the Haxe4E authors.
 * SPDX-License-Identifier: EPL-2.0
 */
package org.haxe4e.studio;

import org.osgi.framework.BundleContext;

import de.sebthom.eclipse.commons.AbstractEclipsePlugin;
import de.sebthom.eclipse.commons.BundleResources;
import de.sebthom.eclipse.commons.logging.PluginLogger;
import de.sebthom.eclipse.commons.logging.StatusFactory;
import net.sf.jstuff.core.validation.Assert;

/**
 * @author Sebastian Thomschke
 */
public class HaxeStudioPlugin extends AbstractEclipsePlugin {

   private static HaxeStudioPlugin instance;

   /**
    * @return the shared instance
    */
   public static HaxeStudioPlugin get() {
      Assert.notNull(instance, "Default plugin instance is still null.");
      return instance;
   }

   public static PluginLogger log() {
      return get().getLogger();
   }

   public static BundleResources resources() {
      return get().getBundleResources();
   }

   public static StatusFactory status() {
      return get().getStatusFactory();
   }

   @Override
   public BundleResources getBundleResources() {
      if (bundleResources == null) {
         bundleResources = new BundleResources(this, "src/main/resources");
      }
      return bundleResources;
   }

   @Override
   public void start(final BundleContext context) throws Exception {
      super.start(context);
      instance = this;

   }

   @Override
   public void stop(final BundleContext context) throws Exception {
      instance = null;
      super.stop(context);
   }
}
