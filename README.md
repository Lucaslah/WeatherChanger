<!-- modrinth_exclude.start -->

<!--suppress HtmlDeprecatedAttribute -->
<p align="center" style="display: block;margin-left: auto;margin-right: auto;margin-bottom: 0">
    <img src="assets/logo-242x242.png" alt="Weather Changer Logo"/>
</p>

<h1 align="center" style="margin-top: 0;margin-bottom: 0">Weather Changer</h1>
<p align="center" style="margin-top: 0;">Client side weather change for Minecraft</p>

<p align="center"><a href="https://www.curseforge.com/minecraft/mc-mods/weather-changer"><img src="https://cf.way2muchnoise.eu/full_682513_downloads.svg" alt="curseforge-downloads"></a>
<a href="https://modrinth.com/mod/weather-changer"><img src="https://img.shields.io/modrinth/dt/nhSHTGyl?logo=Modrinth" alt="modrinth-downloads"></a></p>

<p align="center" style="display: block;margin: 0 auto;">
    <img src="assets/banner.png"  alt="Weather Changer Banner"/>
</p>

<!-- modrinth_exclude.end -->

## Overview
Changes the weather on client side (only visible to you) to clear, rain, or thunder, this mod does not affect the server or send any packets to the server.

Supports forge and fabric, requires the [Fabric API](https://modrinth.com/mod/P7dR8mSH) when using fabric

This checkout targets **Minecraft Java Edition 26.3**, with Java 25,
Fabric Loader 0.19.5 / Fabric API 0.161.0+26.3, or Forge 66.0.9.

### In-Game Command Usage
`/clientweather <off | clear | rain | thunder>`

The command alias `/cweather` may also be used as a shortcut.

### Keybindings
If you would like to set the keybindings, you can do so in the minecraft keybinding settings.

| Description                                                                      | Default |
|----------------------------------------------------------------------------------|---------|
| Disable the client weather (weather will display the server weather like normal) | Unbound |
| Set the client weather mod to clear                                              | Unbound |
| Set the client weather mod to rain                                               | Unbound |   
| Set the client weather mod to thunder                                            | Unbound |

<!-- modrinth_exclude.start -->

## Download
You can download the mod from any of the platforms below.

**Curseforge**: https://www.curseforge.com/minecraft/mc-mods/weather-changer <br>
**Modrinth**: https://modrinth.com/mod/weather-changer <br>
**GitHub Releases:** https://github.com/Lucaslah/WeatherChanger/releases <br>

<!-- modrinth_exclude.end -->

## Building for Minecraft 26.3

Install JDK 25 and run `./gradlew build` (`.\gradlew.bat build` on Windows).
The Gradle wrapper downloads Gradle 9.7.1. Fabric and Forge jars are generated
in `fabric/build/libs` and `forge/build/libs`; jars ending in `-sources` are
for development, not installation.

The 26.3 port uses SDL-compatible `InputConstants.Type.KEYBOARD` and
`InputConstants.UNKNOWN` for initially unbound keys. Weather overrides are
restricted to client worlds where `Level.canHaveWeather()` allows weather.
`off` leaves vanilla weather methods untouched; `rain` suppresses the visual
thunder gradient even when the server has a thunderstorm. Gameplay weather
checks continue to use the server's actual weather.

Porting references: [Fabric 26.3 migration notes](https://fabricmc.net/2026/09/15/263.html)
and [Forge 26.3 downloads](https://files.minecraftforge.net/net/minecraftforge/forge/index_26.3.html).

## Supported Versions

Supported versions may receive bug fixes,
older Minecraft versions may work with older
Weather Changer versions, but are not supported.

See the [GitHub releases](https://github.com/Lucaslah/WeatherChanger/releases) page for full release history.

| Weather Changer Version | Minecraft Version | Mod loaders   | Latest Release                                                                     |
|-------------------------|-------------------|---------------|------------------------------------------------------------------------------------|
| v1.4.x                  | 26.1-26.2              | Fabric, Forge | [1.4.2](https://github.com/Lucaslah/WeatherChanger/releases/tag/1.4.2) |
| v1.3.x                  | 1.21.9-1.21.11    | Fabric, Forge | [1.3.0-beta1](https://github.com/Lucaslah/WeatherChanger/releases/tag/1.3.0-beta1) |
| v1.2.x                  | 1.21-1.21.8       | Fabric, Forge | [1.2.2](https://github.com/Lucaslah/WeatherChanger/releases/tag/1.2.2)             |

------------------------------------------
*Licensed under the [GNU Lesser General Public License v3.0](https://www.gnu.org/licenses/lgpl-3.0.en.html) license.*
