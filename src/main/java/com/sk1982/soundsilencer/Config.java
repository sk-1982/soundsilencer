package com.sk1982.soundsilencer;

import ninja.leaping.configurate.objectmapping.Setting;
import ninja.leaping.configurate.objectmapping.serialize.ConfigSerializable;

import java.util.*;

@ConfigSerializable
public class Config {

    @Setting(comment = "Log canceled sound broadcasts")
    Boolean logCancels = true;

    @Setting(comment = "Log sound broadcasts that were not cancelled")
    Boolean logNonCancels = true;

    @Setting(comment = "Sound broadcast IDs blacklist/whitelist")
    List<String> muteList = Arrays.asList("minecraft:entity.wither.spawn", "minecraft:entity.enderdragon.death");

    @Setting(comment = "Use muteList as a whitelist of allowed sounds instead of a blacklist")
    Boolean whitelist = false;

    @Setting(comment = "Converts broadcast sounds to local sounds heard only in a set radius")
    Boolean convertToLocal = true;

    @Setting(comment = "Default volume for local sounds\na value less than 0 will result in the original volume\nrequires convertToLocal = true")
    Float defaultVolume = -1.0f;

    @Setting(comment = "Volume map for specific sounds\na value less than 0 will result in the original volume\nrequires convertToLocal = true")
    HashMap<String, Float> volumeMap = new HashMap<String, Float>() {{
        put("minecraft:entity.wither.spawn", .25f);
    }};
}
