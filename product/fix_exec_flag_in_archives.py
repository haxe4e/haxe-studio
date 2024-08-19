# SPDX-FileCopyrightText: © Haxe4E authors
# SPDX-FileContributor: Sebastian Thomschke
# SPDX-License-Identifier: EPL-2.0
# SPDX-ArtifactOfProjectHomePage: https://github.com/haxe4e/haxe4e-studio
#
# Sets the missing executable flag in tar.gz files produced for Linux/MacOS when building on Windows
# https://bugs.eclipse.org/bugs/show_bug.cgi?id=442607

import fnmatch, os, tarfile


def set_executable(archivepath:str, files_to_change:list[str]):
    print(f"Fixing file permission in {archivepath}...")

    os.rename(archivepath, f"{archivepath}.orig")

    source:tarfile.TarFile
    target:tarfile.TarFile

    with tarfile.open(f"{archivepath}.orig", "r:gz") as source:
        with tarfile.open(archivepath, "w:gz", compresslevel = 9) as target:
            entry: tarfile.TarInfo
            for entry in source.getmembers():
                if any(fnmatch.fnmatch(entry.path, pattern) for pattern in files_to_change):
                    entry.mode = 0o755

                if entry.isfile():
                    target.addfile(entry, source.extractfile(entry))
                else:
                    target.addfile(entry)

    os.remove(f"{archivepath}.orig")


cwd = os.path.dirname(__file__)

set_executable(f"{cwd}/target/products/org.haxe4e.studio-linux.gtk.x86_64.tar.gz", [
    "HaxeStudio",
    "plugins/org.eclipse.justj.openjdk.hotspot.jre.*/jre/bin/java"])

set_executable(f"{cwd}/target/products/org.haxe4e.studio-macosx.cocoa.x86_64.tar.gz", [
    "Eclipse.app/Contents/MacOS/HaxeStudio",
    "Eclipse.app/Contents/Eclipse/plugins/org.eclipse.justj.openjdk.hotspot.jre.*/jre/bin/java"])
