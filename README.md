# 💛 CodiHI — Health Indicator
### PlaceholderAPI Expansion for Minecraft

> A lightweight Minecraft plugin that displays player health using PlaceholderAPI placeholders with visual heart indicators.

---

## 🖼️ Preview

![Scoreboard Preview](https://raw.githubusercontent.com/your-repo/main/images/scoreboard.png)
![Nameplate Preview](https://raw.githubusercontent.com/your-repo/main/images/nameplate.png)

> 📹 Video preview available — showcased live on **CodiSMP Network** (codismp.xyz)

---

## ❓ What does this plugin do?

**CodiHI** (CodiHealthIndicator) adds placeholders that show player health information in various formats. You can display health as numbers, heart symbols (❤), or a combination of both. The plugin also supports **absorption hearts** (the yellow/golden hearts you get from effects like the Golden Apple and Totem of Undying).

---

## 📦 Requirements

| Requirement | Details |
|---|---|
| [PlaceholderAPI](https://www.spigotmc.org/resources/placeholderapi.6245/) | ⚠️ Required — plugin will not work without this! |
| Minecraft Server | 1.20.1 or higher |
| Server Software | Paper, Spigot, or Folia |

---

## 🚀 Installation

1. Make sure **PlaceholderAPI** is installed first
2. Drop `CodiHI.jar` into your `/plugins` folder
3. Restart your server
4. Done! The placeholders are now available

---

## 📌 Placeholders

Use these placeholders anywhere PlaceholderAPI is supported (chat plugins, scoreboard plugins, tab list plugins, etc.):

### Basic Placeholders

| Placeholder | Description | Example Output |
|---|---|---|
| `%codihi_health%` | Current health with a heart icon | `20 ❤` (red) or `24 ❤` (yellow when absorbing) |
| `%codihi_health_current%` | Current health as a plain number | `20` |
| `%codihi_health_max%` | Maximum health as a plain number | `20` |


### 💡 Notes
- `%codihi_health%` automatically switches to a **yellow heart** when the player has absorption active
- All values are **rounded** to the nearest integer

---

## 🔧 Example Configurations

### In Chat

Use with chat plugins like **EssentialsX** or **ChatControl**:

```
[%codihi_health%] PlayerName: Hello!
```

Shows as:
```
[20 ❤] CodiMC: Hello!
```

---

### TAB — Player List (Tab Menu)
> [TAB Playerlist Objective Guide](https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Playerlist-Objective)

```yaml
playerlist-objective:
  enabled: true
  value: ""
  fancy-value: "%codihi_health%"
  title: "Java Edition is better" # Only visible on Bedrock Edition
  render-type: INTEGER
  disable-condition: '%world%=disabledworld'
```

*Displays the player's health in the tab list next to their name.*

---

### TAB — Belowname
> [TAB Belowname Guide](https://github.com/NEZNAMY/TAB/wiki/Feature-guide:-Belowname)

```yaml
belowname-objective:
  enabled: true
  value: ""
  title: ""
  fancy-value: "%codihi_health%"
  fancy-value-default: "NPC"
  disable-condition: '%world%=disabledworld'
```

*Displays the player's health below their nameplate in-game.*

---

## 💛 Heart Colors

| Color | Meaning |
|---|---|
| ❤ Red Hearts | Normal health |
| ❤ Yellow Hearts | Absorption (from Golden Apples, Absorption potions, etc.) |

---

## 🖥️ Compatibility

| Platform | Supported |
|---|---|
| Paper | ✅ |
| Spigot | ✅ |
| Folia | ✅ Full thread-safety support |

The plugin automatically detects your server type on startup.

---

## 🔨 Commands

This plugin has **no commands**. Just install it and use the placeholders!

---

## 🔑 Permissions

This plugin requires **no permissions**. All placeholders work for everyone.

---

## 🆘 Support

If the plugin doesn't load or you have encountered any bugs, please check out our Discord server:
👉 [discord.gg/JCNtjbwnue](https://discord.gg/JCNtjbwnue)

---

## 📊 Resource Info

| Field | Details |
|---|---|
| **Version** | v1.0.2 |
| **Published** | Dec 23, 2025 |
| **Last Updated** | Mar 6, 2026 |
| **Downloads** | 82 |
| **File Size** | 31.6 KB |
| **License** | Free EULA |
| **Open Source** | Yes |

---

## 🏷️ Tags

`health` · `indicator` · `minecraft` · `placeholderapi` · `plugin` · `folia`

---

## 🔗 Links

- 📥 [Download on BuiltByBit](https://builtbybit.com/resources/health-indicator.86969/)
- 💬 [Discord Support](https://discord.gg/JCNtjbwnue)
- 🌐 Showcased on: **codismp.xyz**

---

## 👤 Author

Made with ❤️ by **CodiMC**
