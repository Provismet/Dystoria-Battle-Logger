# **Cobblemon Battle Logger**

The **Cobblemon Battle Logger** is a mod designed to track and log:
- **PvP (Player vs. Player) battles**,
- **PvN (Player vs. NPC) battles**,
- **PvW (Player vs. Wild Pokémon) battles**.

It records the teams used and battle outcomes in a log file for later review, making it a handy tool for both players and server admins.

---

## **Features**

### **Server-Side**
- Configure the mod using the `/battlelogger` command.
- Supported subcommands:
    - `/battlelogger PvP <true|false>` – Enable/disable logging for PvP battles.
    - `/battlelogger PvN <true|false>` – Enable/disable logging for PvN battles.
    - `/battlelogger PvW <true|false>` – Enable/disable logging for PvW battles.
    - Running any subcommand **without** `true/false` will display the current setting.

### **Client-Side (Optional)**
- Players can save logs provided from the server of their own battles.
- This behaviour is disabled by default but can be accessed via Mod Menu if Cloth Config is also installed.
