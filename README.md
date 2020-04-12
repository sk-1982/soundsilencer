# Sound Silencer

A configurable plugin to silence any chosen sound broadcast, such as withers and dragons. **Requires at least Sponge 7.2.0.**

This is the GitHub repo for the project, please see the main page [here.](https://ore.spongepowered.org/sk1982/Sound-Silencer)

### Configuration

Edit the configuration file in `config/soundsilencer.conf` with your desired values, and use `/sponge plugins reload` to reload config values.

| Key | Description | Type | Default |
| ----------- | ----------- | ----------- | ----------- |
| `logCancels` | Log canceled sound broadcasts | `Boolean` | `true` |
| `logNonCancels` | Log sound broadcasts that were not canceled | `Boolean` | `true` |
| `muteList` | Blacklist of disallowed sound broadcast IDs | `List<String>` | <code>[<br>&nbsp;&nbsp;&nbsp;&nbsp;"minecraft:entity.wither.spawn",<br>&nbsp;&nbsp;&nbsp;&nbsp;"minecraft:entity.enderdragon.death" ]</code> |
| `whitelist` | Use `muteList` as a whitelist of allowed sounds instead of a blacklist | `Boolean` | `false` |
| `convertToLocal` | Converts broadcast sounds to local sounds heard only in a set radius | `Boolean` | `true` |
| `defaultVolume` | Default volume for local sounds, value less than 0 will result in original volume. Requires `convertToLocal = true` | `Float` | `-1` |
| `volumeMap` | Specific volumes for local sounds to use instead of `defaultVolume`. Requires `convertToLocal = true` | `Map<String, Float>` | <code>{<br>&nbsp;&nbsp;&nbsp;&nbsp;"minecraft:entity.wither.spawn"=0.25<br>}</code> |