package com.openmodloader.loader;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.io.*;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class ModInfo {

    private String modid;
    private String name;
    private String version;
    private String mcversion;
    private String side = "";
    private String mainClass = "";
    private String languageAdapter = "com.openmodloader.loader.language.JavaLanguageAdapter";
    private String [] mixins = new String[]{};
	private String [] clientMixins = new String[]{};
	private String [] serverMixins = new String[]{};
	private String [] dependencies = new String[]{};

	public ModInfo(String modid) {
        this.modid = modid;
    }

    public ModInfo() {
    }

    @Nullable
    public static ModInfo[] readFromJar(@Nonnull JarFile file) throws IOException {
        ZipEntry entry = file.getEntry("mod.json");
        if (entry == null)
            return null;
        return OpenModLoader.getGson().fromJson(new InputStreamReader(file.getInputStream(entry)), ModInfo[].class);
    }

    public static ModInfo[] readFromFile(File modJson) throws FileNotFoundException {
        return OpenModLoader.getGson().fromJson(new InputStreamReader(new FileInputStream(modJson)), ModInfo[].class);
    }

    public String getModId() {
        return modid;
    }

    public String getName() {
        return name;
    }

    public String getVersion() {
        return version;
    }

    public String getMcVersion() {
        return mcversion;
    }

    public String getSide() {
        return side;
    }

    public String getMainClass() {
        return mainClass;
    }

    public String getLanguageAdapter() {
        return languageAdapter;
    }

	public String[] getMixins() {
		return mixins;
	}

	public String[] getClientMixins() {
		return clientMixins;
	}

	public String[] getServerMixins() {
		return serverMixins;
	}

    public String[] getDependencies() {
        return dependencies;
    }
}