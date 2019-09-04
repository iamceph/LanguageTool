package misat11.lib.lang;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class I18nBungee {
    public static final String base_lang_code = "en";

    private static String locale = "en";
    private static Configuration config_baseLanguage;
    private static Configuration config;
    private static Configuration customMessageConfig;

    public static String i18n(String key) {
        return i18n(key, null, true);
    }

    public static String i18nonly(String key) {
        return i18n(key, null, false);
    }

    public static String i18n(String key, boolean prefix) {
        return i18n(key, null, prefix);
    }

    public static String i18n(String key, String defaultK) {
        return i18n(key, defaultK, true);
    }

    public static String i18nonly(String key, String defaultK) {
        return i18n(key, defaultK, false);
    }

    public static String i18n(String key, String defaultK, boolean prefix) {
        String value = "";
        if (prefix) {
            value += translate("prefix", "") + " ";
        }

        if (key != null) {
            value += translate(key, defaultK);
        }

        return ChatColor.translateAlternateColorCodes('&', value);
    }

    private static String translate(String base, String defaultK) {
        if (isSet(base, customMessageConfig)) {
            return customMessageConfig.getString(base);
        } else if (isSet(base, config)) {
            return config.getString(base);
        } else if (config_baseLanguage != null) {
            if (isSet(base,config_baseLanguage)) {
                return config_baseLanguage.getString(base);
            }
        }
        if (defaultK != null) {
            return defaultK;
        }

        return "§c" + base;
    }

    public static void load(Plugin plugin, String loc) {
        if (loc != null) {
            locale = loc;
        }
        if (!base_lang_code.equals(locale)) {
            InputStream inb = plugin.getResourceAsStream("messages_" + base_lang_code + ".yml");
            if (inb != null) {
                config_baseLanguage = ConfigurationProvider.getProvider(YamlConfiguration.class).load(inb);
            }
        }
        InputStream in = plugin.getResourceAsStream("messages_" + locale + ".yml");
        if (in != null) {
            config = ConfigurationProvider.getProvider(YamlConfiguration.class).load(in);
        }
        File customMessageConfigF = new File(plugin.getDataFolder(), "messages_" + locale + ".yml");
        if (customMessageConfigF.exists()) {
            try {
                customMessageConfig = ConfigurationProvider.getProvider(YamlConfiguration.class).load(customMessageConfigF);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        plugin.getLogger()
                .info("Successfully loaded messages for " + plugin.getDescription().getName() + "! Language: " + translate("lang_name", null));
    }

    private static boolean isSet(String path, Configuration config) {
        if (config == null) {
            return false;
        } else {
            return config.get(path, null) != null;
        }
    }
}