# LanguageTool
Small but very usefull tool for loading different languages in Bukkit or BungeeCord plugins!

[![Build Status](https://jenkins.mtorus.cz:443/view/Minecraft%20plugins/job/LanguageTool/badge/icon?style=flat-square)](https://jenkins.mtorus.cz:443/view/Minecraft%20plugins/job/LanguageTool/)

## How to use
1. Import maven repository
```xml
<repository>
  <id>mtorus-repo</id>
  <url>https://jenkins.mtorus.cz/plugin/repository/everything/</url>
</repository>
```
2. Include dependency
```xml
<dependency>
  <groupId>misat11.lib.lang</groupId>
  <artifactId>LanguageTool</artifactId>
  <version>1.0.0</version>
  <scope>compile</scope>
</dependency>
```
3. Use in code
```java
  import static misat11.lib.lang.I18n.*;
  
  String myLocale = "en";
  (SPIGOT)
  load(this, myLocale);

  (BUNGEE)
  loadBungee(this, myLocale)


  
  ....
  
  String translate1 = i18n("my_key"); // return: [prefix] my_key
  String translate2 = i18nonly("my_key"); // return: my_key
  String translate3 = i18n("my_key", false); // return: my_key
  translate2.equals(translate3) // return true
  String translate4 = i18n("my_key", "Default value"); // return: [prefix] my_key or [prefix] Default value if my_key isn't exist
  String translate5 = i18nonly("my_key", "Default value"); // return: my_key or Default value if my_key isn't exist
  translate4.equals(translate5) // return true
  String translate6 = i18n("my_key", "Default value", false); // return: my_key or Default value if my_key isn't exist
  
  ....
```
4. Relocate package
```xml
<build>
  <plugins>
    <plugin>
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-shade-plugin</artifactId>
      <executions>
        <execution>
          <phase>package</phase>
          <goals>
            <goal>shade</goal>
          </goals>
          <configuration>
            <relocations>
              <relocation>
                <pattern>misat11.lib</pattern>
                <shadedPattern>yourcustompackage.lib</shadedPattern>
              </relocation>
            </relocations>
          </configuration>
        </execution>
      </executions>
    </plugin>
  </plugins>
</build>
  ```
5. Now build your plugin and enjoy it!
`mvn install`

## Languages file
Put languages file to your jar with name `messages_<langcode>.yml`
You can put file to plugin data folder too.

If something is missing (full language or part), it'll be loaded from `messages_en.yml`

```yaml
lang_name: "My Language"

prefix: "[My Best Plugin]"

# Your translates

my_key: "This is my translate!"
```
