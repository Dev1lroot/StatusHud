# StatusHUD

![Minecraft Version](https://img.shields.io/badge/Minecraft-26.1-green.svg)
![Loader](https://img.shields.io/badge/Loader-NeoForge-orange.svg)
[![License: GPL v3](https://img.shields.io/badge/License-GPLv3-blue.svg)](https://www.gnu.org/licenses/gpl-3.0)

**StatusHUD** is a lightweight client-side mod for Minecraft (NeoForge) that enhances your in-game HUD with useful real-time indicators. It is designed to provide quick access to important gameplay information without cluttering the screen.

## Features

- Damage Indicator
- Active Tool Durability Indicator 

![StatusHUD Preview](screenshots/2026-03-27_21.07.16.png)

- InGame Time Indicator
- Target Position Indicator (chunk coordinates), very helpful for finding buried treasures.

![StatusHUD Preview](screenshots/2026-03-27_21.08.16.png)

## Configuration

All features can be enabled / disabled / customized via the config file.

### Available Options

| Option | Description | Default |
|------|------------|--------|
| `bEnableDamageIndicator` | Toggle damage indicator | `true` |
| `bEnableTimeIndicator` | Toggle time display | `true` |
| `bEnableDateIndicator` | Toggle date display *(WIP)* | `false` |
| `bEnableToolDurabilityIndicator` | Toggle tool durability display | `true` |
| `bEnableToolTargetPositionIndicator` | Toggle target block position display | `true` |
| `sTimeIndicatorMode` | Time source: `real` or `game` *(WIP)* | `game` |
| `sDateIndicatorMode` | Date source: `real` or `game` *(WIP)* | `game` |

---

## WIP stands for "Work In Progress"

Some features are still under development and may not work as expected:

- Date Indicator
- Time mode switching
- Date mode switching

Expect bugs or incomplete behavior in these areas.

---

## Installation

1. Install **NeoForge** for your Minecraft version.
2. Download the latest release of **StatusHUD**.
3. Place the `.jar` file into your `mods` folder.
4. Launch the game.

---

## License

This project is licensed under the **GNU General Public License v3.0**.  
You are free to use, modify, and distribute this software under the same license.

See the `LICENSE` file for more details.

---

## Notes

- This mod is purely client-side.
- Designed to be minimalistic and non-intrusive.
- Configuration is accessible via standard NeoForge config system.

---

## Future Plans

- Fully functional date/time modes
- Customizable HUD positioning
- Additional indicators (armor durability, entity armor, biome, weather, moon phase, etc.)
- UI scaling and styling options

---

## Contributing

Contributions, suggestions, and bug reports are welcome.  
Feel free to open an issue or submit a pull request.