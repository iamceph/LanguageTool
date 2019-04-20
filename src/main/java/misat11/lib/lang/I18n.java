package misat11.lib.lang;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public class I18n {

	public static final String base_lang_code = "en";

	private static String locale = "en";
	private static FileConfiguration config_baseLanguage;
	private static FileConfiguration config;
	private static FileConfiguration customMessageConfig;

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
		value += translate(key, defaultK);
		return value;
	}

	private static String translate(String base, String defaultK) {
		if (customMessageConfig.isSet(base)) {
			return customMessageConfig.getString(base);
		} else if (config.isSet(base)) {
			return config.getString(base);
		} else if (config_baseLanguage != null) {
			if (config_baseLanguage.isSet(base)) {
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
			InputStream inb = plugin.getResource("messages_" + base_lang_code + ".yml");
			config_baseLanguage = new YamlConfiguration();
			try {
				config_baseLanguage.load(new InputStreamReader(inb, StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		InputStream in = plugin.getResource("messages_" + locale + ".yml");
		config = new YamlConfiguration();
		if (in != null) {
			try {
				config.load(new InputStreamReader(in, StandardCharsets.UTF_8));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		customMessageConfig = new YamlConfiguration();
		File customMessageConfigF = new File(plugin.getDataFolder(), "messages_" + locale + ".yml");
		if (customMessageConfigF.exists()) {
			try {
				customMessageConfig.load(customMessageConfigF);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
		plugin.getLogger()
				.info("Successfully loaded messages for " + plugin.getName() + "! Language: " + translate("lang_name", null));
	}

}
